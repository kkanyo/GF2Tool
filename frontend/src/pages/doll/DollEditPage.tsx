import { useEffect, useMemo, useState } from 'react';
import { Navigate, useLocation, useNavigate, useParams } from 'react-router-dom';
import globalAxios from 'axios';
import {
  BulletType,
  DollApi,
  DollRare,
  Job,
  PhaseAttribute,
  Squad,
  WeaponType,
  type DollResponseDto,
  type DollSaveRequestDto,
  type DollStatSaveRequestDto,
  type DollWithStatSaveRequestDto,
} from '../../api/generated';
import { BASE_PATH } from '../../api/generated/base';
import { NumberField, SelectField, TextField } from '../../components/common/Index';
import { useTranslation } from 'react-i18next';

interface LocationState {
  doll: DollResponseDto;
}

type StatFetchState =
  | { kind: 'loading' }
  | { kind: 'ready' }
  | { kind: 'error'; message: string };

type SubmitState = 'idle' | 'submitting' | 'error';

const STAT_DEFAULTS: DollStatSaveRequestDto = {
  attack: 0,
  defense: 0,
  health: 0,
  stability: 0,
  criticalRate: 0,
  criticalDamage: 0,
  mobility: 0,
  attackBonus: 0,
  defenseBonus: 0,
  healthBonus: 0,
  weakness1: BulletType.LightAmmo,
  weakness2: PhaseAttribute.None,
};

function DollEditPage() {
  const { id } = useParams<{ id: string }>();
  const location = useLocation();
  const navigate = useNavigate();
  const { t } = useTranslation();

  const api = useMemo(() => new DollApi(), []);

  const locationState = location.state as LocationState | null;
  const initialDoll = locationState?.doll ?? null;
  const dollId = id == null ? Number.NaN : Number(id);

  const [dollForm, setDollForm] = useState<DollSaveRequestDto>({
    name: initialDoll?.name ?? '',
    attribute: initialDoll?.attribute ?? PhaseAttribute.None,
    rare: initialDoll?.rare ?? DollRare.Standard,
    weaponType: initialDoll?.weaponType ?? WeaponType.Hg,
    job: initialDoll?.job ?? Job.Sentinel,
    squad: Squad.None,
  });

  const [statForm, setStatForm] = useState<DollStatSaveRequestDto>(STAT_DEFAULTS);
  const [fetchState, setFetchState] = useState<StatFetchState>({ kind: 'loading' });
  const [submitState, setSubmitState] = useState<SubmitState>('idle');
  const [errorMessage, setErrorMessage] = useState('');

  // Fetch current stats to pre-fill the form
  useEffect(() => {
    if (!initialDoll || Number.isNaN(dollId)) return;

    let cancelled = false;

    const run = async () => {
      try {
        const res = await api.getDollStat(dollId);
        if (cancelled) return;

        const s = res.data;
        setStatForm({
          attack: s.attack ?? 0,
          defense: s.defense ?? 0,
          health: s.health ?? 0,
          stability: s.stability ?? 0,
          criticalRate: s.criticalRate ?? 0,
          criticalDamage: s.criticalDamage ?? 0,
          mobility: s.mobility ?? 0,
          attackBonus: s.attackBonus ?? 0,
          defenseBonus: s.defenseBonus ?? 0,
          healthBonus: s.healthBonus ?? 0,
          weakness1: s.weakness1 ?? BulletType.LightAmmo,
          weakness2: s.weakness2 ?? PhaseAttribute.None,
        });
        setFetchState({ kind: 'ready' });
      } catch (e) {
        if (cancelled) return;
        const message = e instanceof Error ? e.message : t('dollEdit.fetchError');
        setFetchState({ kind: 'error', message });
      }
    };

    void run();
    return () => {
      cancelled = true;
    };
  }, [api, dollId, initialDoll, t]);

  // Redirect if state is missing or id is invalid (e.g. direct URL access)
  if (!initialDoll || Number.isNaN(dollId)) {
    return <Navigate to="/dolls/list" replace />;
  }

  const handleSubmit = async () => {
    setSubmitState('submitting');
    setErrorMessage('');

    const requestDto: DollWithStatSaveRequestDto = {
      doll: dollForm,
      dollStat: statForm,
    };

    try {
      // REST: PUT /api/v1/dolls/{id} – full replacement of doll + stat
      await globalAxios.put(`${BASE_PATH}/api/v1/dolls/${dollId}`, requestDto, {
        headers: { 'Content-Type': 'application/json', Accept: '*/*' },
      });

      setSubmitState('idle');
      navigate(`/dolls/${dollId}`, {
        state: { doll: { ...initialDoll, ...dollForm, id: dollId } },
      });
    } catch (e) {
      const msg = e instanceof Error ? e.message : t('dollEdit.error');
      setErrorMessage(msg);
      setSubmitState('error');
    }
  };

  const isFormReady = fetchState.kind === 'ready';

  return (
    <div className="min-h-dvh bg-zinc-950 text-zinc-100">
      <div className="mx-auto w-full max-w-4xl px-4 py-8">
        {/* Back button */}
        <button
          type="button"
          className="mb-6 inline-flex items-center gap-1 text-sm text-zinc-400 transition-colors hover:text-zinc-100"
          onClick={() =>
            navigate(`/dolls/${dollId}`, { state: { doll: initialDoll } })
          }
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
          {t('dollEdit.backToDetail')}
        </button>

        <h1 className="text-2xl font-bold tracking-tight">{t('dollEdit.title')}</h1>
        <p className="mt-1 text-sm text-zinc-400">{initialDoll.name}</p>

        {/* Stat loading / error */}
        {fetchState.kind === 'loading' && (
          <p className="mt-6 text-sm text-zinc-400">{t('dollEdit.loadingStats')}</p>
        )}
        {fetchState.kind === 'error' && (
          <div className="mt-6 rounded-lg border border-red-800 bg-red-900/20 p-4">
            <p className="text-sm font-medium text-red-300">{t('dollEdit.fetchError')}</p>
            <p className="mt-1 text-xs text-zinc-400">{fetchState.message}</p>
          </div>
        )}

        {isFormReady && (
          <div className="mt-6 flex flex-col gap-6">
            {/* Basic info section */}
            <section className="rounded-xl border border-zinc-800 bg-zinc-900 p-5">
              <h2 className="mb-4 text-sm font-semibold text-zinc-200">
                {t('dollDetail.basicInfo')}
              </h2>
              <div className="flex flex-col gap-4">
                <TextField
                  label={t('doll.name')}
                  value={dollForm.name ?? ''}
                  onChange={(name) => setDollForm({ ...dollForm, name })}
                />
                <SelectField
                  label={t('doll.attribute')}
                  value={dollForm.attribute}
                  options={Object.values(PhaseAttribute)}
                  textPrefix="enums.attributes"
                  onChange={(attribute) => setDollForm({ ...dollForm, attribute })}
                />
                <SelectField
                  label={t('doll.rare')}
                  value={dollForm.rare}
                  options={Object.values(DollRare)}
                  textPrefix="enums.rares"
                  onChange={(rare) => setDollForm({ ...dollForm, rare })}
                />
                <SelectField
                  label={t('doll.weaponType')}
                  value={dollForm.weaponType}
                  options={Object.values(WeaponType)}
                  textPrefix="enums.weaponTypes"
                  onChange={(weaponType) => setDollForm({ ...dollForm, weaponType })}
                />
                <SelectField
                  label={t('doll.job')}
                  value={dollForm.job}
                  options={Object.values(Job)}
                  textPrefix="enums.jobs"
                  onChange={(job) => setDollForm({ ...dollForm, job })}
                />
                <SelectField
                  label={t('doll.squad')}
                  value={dollForm.squad}
                  options={Object.values(Squad)}
                  textPrefix="enums.squads"
                  onChange={(squad) => setDollForm({ ...dollForm, squad })}
                />
              </div>
            </section>

            {/* Stats section */}
            <section className="rounded-xl border border-zinc-800 bg-zinc-900 p-5">
              <h2 className="mb-4 text-sm font-semibold text-zinc-200">{t('doll.stats')}</h2>
              <div className="grid grid-cols-1 gap-4 sm:grid-cols-2">
                <NumberField
                  label={t('doll.attack')}
                  value={statForm.attack}
                  onChange={(attack) => setStatForm({ ...statForm, attack })}
                />
                <NumberField
                  label={t('doll.defense')}
                  value={statForm.defense}
                  onChange={(defense) => setStatForm({ ...statForm, defense })}
                />
                <NumberField
                  label={t('doll.health')}
                  value={statForm.health}
                  onChange={(health) => setStatForm({ ...statForm, health })}
                />
                <NumberField
                  label={t('doll.stability')}
                  value={statForm.stability}
                  onChange={(stability) => setStatForm({ ...statForm, stability })}
                />
                <NumberField
                  label={t('doll.critical')}
                  value={statForm.criticalRate}
                  onChange={(criticalRate) => setStatForm({ ...statForm, criticalRate })}
                />
                <NumberField
                  label={t('doll.criticalDamage')}
                  value={statForm.criticalDamage}
                  onChange={(criticalDamage) => setStatForm({ ...statForm, criticalDamage })}
                />
                <NumberField
                  label={t('doll.mobility')}
                  value={statForm.mobility}
                  onChange={(mobility) => setStatForm({ ...statForm, mobility })}
                />
                <NumberField
                  label={t('doll.attackBonus')}
                  value={statForm.attackBonus}
                  onChange={(attackBonus) => setStatForm({ ...statForm, attackBonus })}
                />
                <NumberField
                  label={t('doll.defenseBonus')}
                  value={statForm.defenseBonus}
                  onChange={(defenseBonus) => setStatForm({ ...statForm, defenseBonus })}
                />
                <NumberField
                  label={t('doll.healthBonus')}
                  value={statForm.healthBonus}
                  onChange={(healthBonus) => setStatForm({ ...statForm, healthBonus })}
                />
                <SelectField
                  label={t('dollRegister.weakness1')}
                  value={statForm.weakness1}
                  options={Object.values(BulletType)}
                  textPrefix="enums.bulletTypes"
                  onChange={(weakness1) => setStatForm({ ...statForm, weakness1 })}
                />
                <SelectField
                  label={t('dollRegister.weakness2')}
                  value={statForm.weakness2}
                  options={Object.values(PhaseAttribute)}
                  textPrefix="enums.attributes"
                  onChange={(weakness2) => setStatForm({ ...statForm, weakness2 })}
                />
              </div>
            </section>

            {/* Submit error */}
            {submitState === 'error' && (
              <div className="rounded-lg border border-red-800 bg-red-900/20 p-4">
                <p className="text-sm font-medium text-red-300">{t('dollEdit.error')}</p>
                {errorMessage && (
                  <p className="mt-1 text-xs text-zinc-400">{errorMessage}</p>
                )}
              </div>
            )}

            {/* Save button */}
            <div className="flex justify-end">
              <button
                type="button"
                className="inline-flex h-10 items-center gap-2 rounded-lg bg-indigo-600 px-6 text-sm font-semibold text-white transition-colors hover:bg-indigo-500 disabled:cursor-not-allowed disabled:opacity-50"
                disabled={submitState === 'submitting'}
                onClick={() => void handleSubmit()}
              >
                {submitState === 'submitting' ? t('dollEdit.saving') : t('dollEdit.save')}
              </button>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default DollEditPage;
