import { useEffect, useMemo, useState } from 'react';
import { Navigate, useLocation, useNavigate, useParams } from 'react-router-dom';
import { DollApi, type DollResponseDto, type DollStatResponseDto } from '../../api/generated';
import {
  getDollRareLabel,
  getJobLabel,
  getPhaseAttributeLabel,
  getWeaponTypeLabel,
} from '../../types/dollLabels';
import { useTranslation } from 'react-i18next';

type StatLoadState =
  | { kind: 'loading' }
  | { kind: 'loaded'; data: DollStatResponseDto }
  | { kind: 'error'; message: string };

interface LocationState {
  doll: DollResponseDto;
}

function StatItem({ label, value }: Readonly<{ label: string; value: React.ReactNode }>) {
  return (
    <div className="flex flex-col gap-1 rounded-lg border border-zinc-800 bg-zinc-950/40 p-3">
      <span className="text-xs font-medium text-zinc-400">{label}</span>
      <span className="text-sm font-semibold text-zinc-100">{value ?? '-'}</span>
    </div>
  );
}

function InfoBadge({ label, value }: Readonly<{ label: string; value: string }>) {
  return (
    <div className="flex flex-col gap-1">
      <span className="text-xs font-medium text-zinc-500">{label}</span>
      <span className="rounded-md border border-zinc-700 bg-zinc-800 px-2 py-1 text-sm text-zinc-100">
        {value}
      </span>
    </div>
  );
}

function DollDetailPage() {
  const { id } = useParams<{ id: string }>();
  const location = useLocation();
  const navigate = useNavigate();
  const { t } = useTranslation();

  const api = useMemo(() => new DollApi(), []);

  const locationState = location.state as LocationState | null;
  const doll = locationState?.doll ?? null;

  // Priority is given to state.doll.id and URL parameters are used as fallback.
  // Even if the backend does not return an id in the list API, doll.id can be used.
  const urlId = id == null ? Number.NaN : Number(id);
  const dollId = doll?.id ?? (Number.isNaN(urlId) ? Number.NaN : urlId);

  const [statState, setStatState] = useState<StatLoadState>({ kind: 'loading' });

  // All hooks must be called before any early return.
  // Guard inside the effect: skip the API call when id is unavailable.
  useEffect(() => {
    if (Number.isNaN(dollId)) return;

    let cancelled = false;

    const run = async () => {
      try {
        const res = await api.getDollStat(dollId);
        if (cancelled) return;
        setStatState({ kind: 'loaded', data: res.data });
      } catch (e) {
        if (cancelled) return;
        const message = e instanceof Error ? e.message : t('dollDetail.statsError');
        setStatState({ kind: 'error', message });
      }
    };

    void run();
    return () => {
      cancelled = true;
    };
  }, [api, dollId, t]);

  // Redirect only if there is no doll (id absence is supported by stat hiding).
  if (!doll) {
    return <Navigate to="/dolls/list" replace />;
  }

  const rareBadgeColor =
    doll.rare === 'ELITE'
      ? 'border-yellow-600 bg-yellow-900/30 text-yellow-300'
      : 'border-zinc-600 bg-zinc-800 text-zinc-300';

  return (
    <div className="min-h-dvh bg-zinc-950 text-zinc-100">
      <div className="mx-auto w-full max-w-4xl px-4 py-8">
        {/* Navigation row: back button + edit button */}
        <div className="mb-6 flex items-center justify-between">
          <button
            type="button"
            className="inline-flex items-center gap-1 text-sm text-zinc-400 transition-colors hover:text-zinc-100"
            onClick={() => navigate('/dolls/list')}
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="16"
              height="16"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              strokeWidth="2"
              strokeLinecap="round"
              strokeLinejoin="round"
            >
              <path d="M15 18l-6-6 6-6" />
            </svg>
            {t('dollDetail.backToList')}
          </button>

          <button
            type="button"
            className="inline-flex h-9 items-center gap-2 rounded-lg border border-indigo-700 bg-indigo-900/30 px-4 text-sm font-medium text-indigo-300 transition-colors hover:bg-indigo-800/40 hover:text-indigo-100 disabled:cursor-not-allowed disabled:opacity-40"
            disabled={Number.isNaN(dollId)}
            onClick={() => navigate(`/dolls/${dollId}/edit`, { state: { doll } })}
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="14"
              height="14"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              strokeWidth="2"
              strokeLinecap="round"
              strokeLinejoin="round"
            >
              <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7" />
              <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z" />
            </svg>
            {t('dollDetail.edit')}
          </button>
        </div>

        {/* Header */}
        <div className="flex flex-wrap items-center gap-3">
          <h1 className="text-3xl font-bold tracking-tight">{doll.name ?? '-'}</h1>
          {doll.rare && (
            <span
              className={`rounded-full border px-3 py-0.5 text-xs font-semibold ${rareBadgeColor}`}
            >
              {getDollRareLabel(t, doll.rare)}
            </span>
          )}
        </div>

        <div className="mt-6 flex flex-col gap-6">
          {/* Basic info card */}
          <section className="rounded-xl border border-zinc-800 bg-zinc-900 p-5">
            <h2 className="mb-4 text-sm font-semibold text-zinc-200">
              {t('dollDetail.basicInfo')}
            </h2>
            <div className="grid grid-cols-2 gap-3 sm:grid-cols-4">
              <InfoBadge
                label={t('doll.attribute')}
                value={doll.attribute ? getPhaseAttributeLabel(t, doll.attribute) : '-'}
              />
              <InfoBadge
                label={t('doll.rare')}
                value={doll.rare ? getDollRareLabel(t, doll.rare) : '-'}
              />
              <InfoBadge
                label={t('doll.weaponType')}
                value={doll.weaponType ? getWeaponTypeLabel(t, doll.weaponType) : '-'}
              />
              <InfoBadge
                label={t('doll.job')}
                value={doll.job ? getJobLabel(t, doll.job) : '-'}
              />
            </div>
          </section>

          {/* Stats card */}
          <section className="rounded-xl border border-zinc-800 bg-zinc-900 p-5">
            <h2 className="mb-4 text-sm font-semibold text-zinc-200">{t('doll.stats')}</h2>

            {Number.isNaN(dollId) && (
              <p className="text-sm text-zinc-500">{t('dollDetail.noId')}</p>
            )}

            {!Number.isNaN(dollId) && statState.kind === 'loading' && (
              <p className="text-sm text-zinc-400">{t('dollDetail.loadingStats')}</p>
            )}

            {!Number.isNaN(dollId) && statState.kind === 'error' && (
              <div className="rounded-lg border border-red-800 bg-red-900/20 p-4">
                <p className="text-sm font-medium text-red-300">{t('dollDetail.statsError')}</p>
                <p className="mt-1 text-xs text-zinc-400">{statState.message}</p>
              </div>
            )}

            {!Number.isNaN(dollId) && statState.kind === 'loaded' && (
              <div className="grid grid-cols-2 gap-3 sm:grid-cols-3 lg:grid-cols-4">
                <StatItem label={t('doll.attack')} value={statState.data.attack} />
                <StatItem label={t('doll.defense')} value={statState.data.defense} />
                <StatItem label={t('doll.health')} value={statState.data.health} />
                <StatItem label={t('doll.stability')} value={statState.data.stability} />
                <StatItem label={t('doll.critical')} value={statState.data.criticalRate} />
                <StatItem label={t('doll.criticalDamage')} value={statState.data.criticalDamage} />
                <StatItem label={t('doll.mobility')} value={statState.data.mobility} />
                <StatItem label={t('doll.attackBonus')} value={statState.data.attackBonus} />
                <StatItem label={t('doll.defenseBonus')} value={statState.data.defenseBonus} />
                <StatItem label={t('doll.healthBonus')} value={statState.data.healthBonus} />
                <StatItem
                  label={t('dollRegister.weakness1')}
                  value={
                    statState.data.weakness1
                      ? t(`enums.bulletTypes.${statState.data.weakness1}`)
                      : '-'
                  }
                />
                <StatItem
                  label={t('dollRegister.weakness2')}
                  value={
                    statState.data.weakness2
                      ? getPhaseAttributeLabel(t, statState.data.weakness2)
                      : '-'
                  }
                />
              </div>
            )}
          </section>
        </div>
      </div>
    </div>
  );
}

export default DollDetailPage;
