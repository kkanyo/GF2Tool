import { useEffect, useMemo, useState } from 'react';
import {
  DollApi,
  DollRare,
  type DollResponseDto,
  Job,
  PhaseAttribute,
  type PageDollResponseDto,
  WeaponType,
} from '../../api/generated';
import {
  getDollRareLabel,
  getJobLabel,
  getPhaseAttributeLabel,
  getWeaponTypeLabel,
} from '../../types/dollLabels';
import { useTranslation } from 'react-i18next';

type LoadState =
  | { kind: 'idle' }
  | { kind: 'loading' }
  | { kind: 'loaded'; data: PageDollResponseDto }
  | { kind: 'error'; message: string };

type AllDollsLoadState =
  | { kind: 'idle' }
  | { kind: 'loading' }
  | { kind: 'loaded'; data: DollResponseDto[] }
  | { kind: 'error'; message: string };

const DEFAULT_PAGE_SIZE = 20;
const FULL_FETCH_PAGE_SIZE = 100;

const ATTRIBUTE_OPTIONS = Object.values(PhaseAttribute) as PhaseAttribute[];
const RARE_OPTIONS = Object.values(DollRare) as DollRare[];
const WEAPON_OPTIONS = Object.values(WeaponType) as WeaponType[];
const JOB_OPTIONS = Object.values(Job) as Job[];

function toDisplayValue(value: unknown) {
  if (value === null || value === undefined) return '-';
  if (typeof value === 'string' && value.trim() === '') return '-';
  if (typeof value === 'string') return value;
  if (typeof value === 'number' || typeof value === 'boolean' || typeof value === 'bigint')
    return String(value);
  if (typeof value === 'symbol') return value.toString();
  if (typeof value === 'function') return value.name ? `[Function ${value.name}]` : '[Function]';
  return JSON.stringify(value);
}

function DollListPage() {
  const api = useMemo(() => new DollApi(), []);
  const { t } = useTranslation();

  const [page, setPage] = useState(0);
  const [size, setSize] = useState(DEFAULT_PAGE_SIZE);

  const [nameQuery, setNameQuery] = useState('');
  const [debouncedNameQuery, setDebouncedNameQuery] = useState('');
  const [attributeFilter, setAttributeFilter] = useState<PhaseAttribute | ''>('');
  const [rareFilter, setRareFilter] = useState<DollRare | ''>('');
  const [weaponTypeFilter, setWeaponTypeFilter] = useState<WeaponType | ''>('');
  const [jobFilter, setJobFilter] = useState<Job | ''>('');

  const [state, setState] = useState<LoadState>({ kind: 'idle' });
  const [allDollsState, setAllDollsState] = useState<AllDollsLoadState>({ kind: 'idle' });

  useEffect(() => {
    const t = globalThis.setTimeout(() => setDebouncedNameQuery(nameQuery), 250);
    return () => globalThis.clearTimeout(t);
  }, [nameQuery]);

  const normalizedNameQuery = debouncedNameQuery.trim().toLowerCase();
  const isSearching =
    normalizedNameQuery.length > 0 ||
    attributeFilter !== '' ||
    rareFilter !== '' ||
    weaponTypeFilter !== '' ||
    jobFilter !== '';

  useEffect(() => {
    if (isSearching) return;

    let cancelled = false;

    const run = async () => {
      try {
        setState({ kind: 'loading' });
        const res = await api.getDollList({ page, size, sort: ['name,asc'] });
        if (cancelled) return;
        setState({ kind: 'loaded', data: res.data });
      } catch (e) {
        if (cancelled) return;
        const message = e instanceof Error ? e.message : 'Failed to load dolls.';
        setState({ kind: 'error', message });
      }
    };

    void run();
    return () => {
      cancelled = true;
    };
  }, [api, page, size, isSearching]);

  useEffect(() => {
    if (!isSearching) return;
    if (allDollsState.kind === 'loaded') return;

    let cancelled = false;

    const fetchAllDolls = async () => {
      try {
        setAllDollsState({ kind: 'loading' });
        const dolls: DollResponseDto[] = [];

        let currentPage = 0;
        // Note: API provides `last`, but keep a defensive fallback to avoid infinite loops.
        // `totalPages` exists in `PageDollResponseDto` and is stable while fetching.
        while (true) {
          const res = await api.getDollList({
            page: currentPage,
            size: FULL_FETCH_PAGE_SIZE,
            sort: ['name,asc'],
          });
          if (cancelled) return;

          const content = res.data.content ?? [];
          dolls.push(...content);

          const lastFromApi = res.data.last;
          const totalPages = res.data.totalPages;
          const shouldStopByLast = lastFromApi === true;
          const shouldStopByTotalPages =
            typeof totalPages === 'number' ? currentPage + 1 >= totalPages : false;
          const shouldStopByEmptyPage = content.length === 0;

          if (shouldStopByLast || shouldStopByTotalPages || shouldStopByEmptyPage) break;
          currentPage += 1;
        }

        setAllDollsState({ kind: 'loaded', data: dolls });
      } catch (e) {
        if (cancelled) return;
        const message = e instanceof Error ? e.message : 'Failed to load all dolls.';
        setAllDollsState({ kind: 'error', message });
      }
    };

    void fetchAllDolls();
    return () => {
      cancelled = true;
    };
  }, [api, isSearching, allDollsState.kind]);

  const filteredAllDolls = useMemo(() => {
    if (!isSearching) return [] as DollResponseDto[];
    if (allDollsState.kind !== 'loaded') return [] as DollResponseDto[];

    const q = normalizedNameQuery;
    return allDollsState.data.filter((d) => {
      if (q) {
        const name = d.name ?? '';
        if (!name.toLowerCase().includes(q)) return false;
      }
      if (attributeFilter !== '' && d.attribute !== attributeFilter) return false;
      if (rareFilter !== '' && d.rare !== rareFilter) return false;
      if (weaponTypeFilter !== '' && d.weaponType !== weaponTypeFilter) return false;
      if (jobFilter !== '' && d.job !== jobFilter) return false;
      return true;
    });
  }, [
    isSearching,
    allDollsState,
    normalizedNameQuery,
    attributeFilter,
    rareFilter,
    weaponTypeFilter,
    jobFilter,
  ]);

  const displayDolls = useMemo(() => {
    if (isSearching) {
      const start = page * size;
      return filteredAllDolls.slice(start, start + size);
    }
    if (state.kind !== 'loaded') return [] as DollResponseDto[];
    return state.data.content ?? [];
  }, [isSearching, page, size, filteredAllDolls, state]);

  const totalElements = useMemo(() => {
    if (isSearching) {
      if (allDollsState.kind !== 'loaded') return undefined;
      return filteredAllDolls.length;
    }
    if (state.kind !== 'loaded') return undefined;
    return typeof state.data.totalElements === 'number' ? state.data.totalElements : undefined;
  }, [isSearching, allDollsState.kind, filteredAllDolls, state]);

  const totalPages = useMemo(() => {
    if (isSearching) {
      if (allDollsState.kind !== 'loaded') return 0;
      const elements = filteredAllDolls.length;
      return Math.max(Math.ceil(elements / size), 1);
    }
    if (state.kind !== 'loaded') return 0;
    return state.data.totalPages ?? 0;
  }, [isSearching, allDollsState.kind, filteredAllDolls.length, size, state]);

  const canPrev = isSearching
    ? allDollsState.kind === 'loaded' && page > 0
    : state.kind === 'loaded' && page > 0;

  const canNext = isSearching
    ? allDollsState.kind === 'loaded' && page + 1 < totalPages
    : state.kind === 'loaded'
      ? page + 1 < (state.data.totalPages ?? 0)
      : false;

  const handleReset = () => {
    setNameQuery('');
    setDebouncedNameQuery('');
    setAttributeFilter('');
    setRareFilter('');
    setWeaponTypeFilter('');
    setJobFilter('');
    setPage(0);
  };

  return (
    <div className="min-h-dvh bg-zinc-950 text-zinc-100">
      <div className="mx-auto w-full max-w-6xl px-4 py-8">
        <div className="flex flex-col gap-2 sm:flex-row sm:items-end sm:justify-between">
          <div>
            <h1 className="text-2xl font-semibold tracking-tight">{t('dollList.title')}</h1>
            <p className="mt-1 text-sm text-zinc-400">{t('dollList.description')}</p>
          </div>
        </div>

        <div className="mt-6 grid grid-cols-1 gap-6 lg:grid-cols-[360px_1fr]">
          <aside className="rounded-xl border border-zinc-800 bg-zinc-900 p-4">
            <h2 className="text-sm font-semibold text-zinc-200">
              {t('dollList.searchConditions')}
            </h2>

            <div className="mt-4 flex flex-col gap-4">
              <label className="flex flex-col gap-1">
                <span className="text-xs font-medium text-zinc-400">
                  {t('dollList.nameSearch')}
                </span>
                <input
                  className="h-10 w-full rounded-lg border border-zinc-800 bg-zinc-900 px-3 text-sm text-zinc-100 placeholder:text-zinc-500 focus:border-indigo-400 focus:ring-2 focus:ring-indigo-400/20 focus:outline-none"
                  value={nameQuery}
                  placeholder={t('dollList.namePlaceholder')}
                  onChange={(e) => {
                    setPage(0);
                    setNameQuery(e.target.value);
                  }}
                />
              </label>

              <label className="flex flex-col gap-1">
                <span className="text-xs font-medium text-zinc-400">{t('dollList.attribute')}</span>
                <select
                  className="h-10 rounded-lg border border-zinc-800 bg-zinc-900 px-3 text-sm text-zinc-100 focus:border-indigo-400 focus:ring-2 focus:ring-indigo-400/20 focus:outline-none"
                  value={attributeFilter}
                  onChange={(e) => {
                    setPage(0);
                    setAttributeFilter(e.target.value as PhaseAttribute | '');
                  }}
                >
                  <option value="">{t('dollList.all')}</option>
                  {ATTRIBUTE_OPTIONS.map((opt) => (
                    <option key={opt} value={opt}>
                      {getPhaseAttributeLabel(t, opt)}
                    </option>
                  ))}
                </select>
              </label>

              <label className="flex flex-col gap-1">
                <span className="text-xs font-medium text-zinc-400">{t('dollList.rare')}</span>
                <select
                  className="h-10 rounded-lg border border-zinc-800 bg-zinc-900 px-3 text-sm text-zinc-100 focus:border-indigo-400 focus:ring-2 focus:ring-indigo-400/20 focus:outline-none"
                  value={rareFilter}
                  onChange={(e) => {
                    setPage(0);
                    setRareFilter(e.target.value as DollRare | '');
                  }}
                >
                  <option value="">{t('dollList.all')}</option>
                  {RARE_OPTIONS.map((opt) => (
                    <option key={opt} value={opt}>
                      {getDollRareLabel(t, opt)}
                    </option>
                  ))}
                </select>
              </label>

              <label className="flex flex-col gap-1">
                <span className="text-xs font-medium text-zinc-400">
                  {t('dollList.weaponType')}
                </span>
                <select
                  className="h-10 rounded-lg border border-zinc-800 bg-zinc-900 px-3 text-sm text-zinc-100 focus:border-indigo-400 focus:ring-2 focus:ring-indigo-400/20 focus:outline-none"
                  value={weaponTypeFilter}
                  onChange={(e) => {
                    setPage(0);
                    setWeaponTypeFilter(e.target.value as WeaponType | '');
                  }}
                >
                  <option value="">{t('dollList.all')}</option>
                  {WEAPON_OPTIONS.map((opt) => (
                    <option key={opt} value={opt}>
                      {getWeaponTypeLabel(t, opt)}
                    </option>
                  ))}
                </select>
              </label>

              <label className="flex flex-col gap-1">
                <span className="text-xs font-medium text-zinc-400">{t('dollList.job')}</span>
                <select
                  className="h-10 rounded-lg border border-zinc-800 bg-zinc-900 px-3 text-sm text-zinc-100 focus:border-indigo-400 focus:ring-2 focus:ring-indigo-400/20 focus:outline-none"
                  value={jobFilter}
                  onChange={(e) => {
                    setPage(0);
                    setJobFilter(e.target.value as Job | '');
                  }}
                >
                  <option value="">{t('dollList.all')}</option>
                  {JOB_OPTIONS.map((opt) => (
                    <option key={opt} value={opt}>
                      {getJobLabel(t, opt)}
                    </option>
                  ))}
                </select>
              </label>

              <label className="flex flex-col gap-1">
                <span className="text-xs font-medium text-zinc-400">페이지 크기</span>
                <select
                  className="h-10 rounded-lg border border-zinc-800 bg-zinc-900 px-3 text-sm text-zinc-100 focus:border-indigo-400 focus:ring-2 focus:ring-indigo-400/20 focus:outline-none"
                  value={size}
                  onChange={(e) => {
                    setPage(0);
                    setSize(Number(e.target.value));
                  }}
                >
                  {[10, 20, 50, 100].map((n) => (
                    <option key={n} value={n}>
                      {n}
                    </option>
                  ))}
                </select>
              </label>

              <button
                type="button"
                className="mt-1 inline-flex h-10 items-center justify-center rounded-lg border border-zinc-800 bg-zinc-950 px-3 text-sm font-medium text-zinc-100 hover:bg-zinc-900 disabled:cursor-not-allowed disabled:opacity-50"
                onClick={handleReset}
              >
                {t('dollList.reset')}
              </button>
            </div>
          </aside>

          <section className="overflow-hidden rounded-xl border border-zinc-800 bg-zinc-900">
            <div className="flex items-center justify-between gap-3 border-b border-zinc-800 px-4 py-3">
              <div className="text-sm text-zinc-300">
                {isSearching ? (
                  allDollsState.kind === 'loaded' ? (
                    <>
                      {t('dollList.page')}{' '}
                      <span className="font-semibold text-zinc-100">{page + 1}</span> /{' '}
                      <span className="font-semibold text-zinc-100">{Math.max(totalPages, 1)}</span>
                      {typeof totalElements === 'number' ? (
                        <span className="ml-2 text-zinc-400">
                          ({totalElements} {t('dollList.items')})
                        </span>
                      ) : null}
                    </>
                  ) : (
                    <span className="text-zinc-400">{t('dollList.loading')}</span>
                  )
                ) : state.kind === 'loaded' ? (
                  <>
                    {t('dollList.page')}{' '}
                    <span className="font-semibold text-zinc-100">{page + 1}</span> /{' '}
                    <span className="font-semibold text-zinc-100">{Math.max(totalPages, 1)}</span>
                    {typeof state.data.totalElements === 'number' ? (
                      <span className="ml-2 text-zinc-400">
                        ({state.data.totalElements} {t('dollList.items')})
                      </span>
                    ) : null}
                  </>
                ) : (
                  <span className="text-zinc-400">Loading…</span>
                )}
              </div>

              <div className="flex items-center gap-2">
                <button
                  type="button"
                  className="inline-flex h-9 items-center justify-center rounded-lg border border-zinc-800 bg-zinc-950 px-3 text-sm font-medium text-zinc-100 hover:bg-zinc-900 disabled:cursor-not-allowed disabled:opacity-50"
                  onClick={() => setPage((p) => Math.max(0, p - 1))}
                  disabled={!canPrev}
                >
                  {t('dollList.prev')}
                </button>
                <button
                  type="button"
                  className="inline-flex h-9 items-center justify-center rounded-lg border border-zinc-800 bg-zinc-950 px-3 text-sm font-medium text-zinc-100 hover:bg-zinc-900 disabled:cursor-not-allowed disabled:opacity-50"
                  onClick={() => setPage((p) => p + 1)}
                  disabled={!canNext}
                >
                  {t('dollList.next')}
                </button>
              </div>
            </div>

            <div className="overflow-x-auto">
              <table className="min-w-full text-left text-sm">
                <thead className="bg-zinc-950/40 text-xs tracking-wide text-zinc-400 uppercase">
                  <tr>
                    <th className="px-4 py-3">{t('dollList.name')}</th>
                    <th className="px-4 py-3">{t('dollList.attribute')}</th>
                    <th className="px-4 py-3">{t('dollList.rare')}</th>
                    <th className="px-4 py-3">{t('dollList.weaponType')}</th>
                    <th className="px-4 py-3">{t('dollList.job')}</th>
                  </tr>
                </thead>
                <tbody className="divide-y divide-zinc-800">
                  {!isSearching && state.kind === 'error' ? (
                    <tr>
                      <td className="px-4 py-6 text-zinc-300" colSpan={5}>
                        <div className="flex flex-col gap-2">
                          <p className="font-medium text-red-300">Failed to load.</p>
                          <p className="text-xs text-zinc-400">{state.message}</p>
                        </div>
                      </td>
                    </tr>
                  ) : null}

                  {!isSearching && (state.kind === 'loading' || state.kind === 'idle') ? (
                    <tr>
                      <td className="px-4 py-6 text-zinc-400" colSpan={5}>
                        Loading…
                      </td>
                    </tr>
                  ) : null}

                  {!isSearching && state.kind === 'loaded' && displayDolls.length === 0 ? (
                    <tr>
                      <td className="px-4 py-6 text-zinc-400" colSpan={5}>
                        No results.
                      </td>
                    </tr>
                  ) : null}

                  {!isSearching && state.kind === 'loaded'
                    ? displayDolls.map((d, idx) => {
                        const key = d.id ?? `${d.name ?? 'unknown'}-${idx}`;
                        return (
                          <tr key={key} className="hover:bg-zinc-950/30">
                            <td className="px-4 py-3 font-medium whitespace-nowrap text-zinc-100">
                              {toDisplayValue(d.name)}
                            </td>
                            <td className="px-4 py-3 whitespace-nowrap text-zinc-300">
                              {d.attribute
                                ? getPhaseAttributeLabel(t, d.attribute)
                                : toDisplayValue(d.attribute)}
                            </td>
                            <td className="px-4 py-3 whitespace-nowrap text-zinc-300">
                              {d.rare ? getDollRareLabel(t, d.rare) : toDisplayValue(d.rare)}
                            </td>
                            <td className="px-4 py-3 whitespace-nowrap text-zinc-300">
                              {d.weaponType
                                ? getWeaponTypeLabel(t, d.weaponType)
                                : toDisplayValue(d.weaponType)}
                            </td>
                            <td className="px-4 py-3 whitespace-nowrap text-zinc-300">
                              {d.job ? getJobLabel(t, d.job) : toDisplayValue(d.job)}
                            </td>
                          </tr>
                        );
                      })
                    : null}

                  {isSearching && allDollsState.kind === 'error' ? (
                    <tr>
                      <td className="px-4 py-6 text-zinc-300" colSpan={5}>
                        <div className="flex flex-col gap-2">
                          <p className="font-medium text-red-300">Failed to load.</p>
                          <p className="text-xs text-zinc-400">{allDollsState.message}</p>
                        </div>
                      </td>
                    </tr>
                  ) : null}

                  {isSearching &&
                  (allDollsState.kind === 'loading' || allDollsState.kind === 'idle') ? (
                    <tr>
                      <td className="px-4 py-6 text-zinc-400" colSpan={5}>
                        Loading…
                      </td>
                    </tr>
                  ) : null}

                  {isSearching &&
                  allDollsState.kind === 'loaded' &&
                  filteredAllDolls.length === 0 ? (
                    <tr>
                      <td className="px-4 py-6 text-zinc-400" colSpan={5}>
                        No results.
                      </td>
                    </tr>
                  ) : null}

                  {isSearching && allDollsState.kind === 'loaded'
                    ? displayDolls.map((d, idx) => {
                        const key = d.id ?? `${d.name ?? 'unknown'}-${idx}`;
                        return (
                          <tr key={key} className="hover:bg-zinc-950/30">
                            <td className="px-4 py-3 font-medium whitespace-nowrap text-zinc-100">
                              {toDisplayValue(d.name)}
                            </td>
                            <td className="px-4 py-3 whitespace-nowrap text-zinc-300">
                              {d.attribute
                                ? getPhaseAttributeLabel(t, d.attribute)
                                : toDisplayValue(d.attribute)}
                            </td>
                            <td className="px-4 py-3 whitespace-nowrap text-zinc-300">
                              {d.rare ? getDollRareLabel(t, d.rare) : toDisplayValue(d.rare)}
                            </td>
                            <td className="px-4 py-3 whitespace-nowrap text-zinc-300">
                              {d.weaponType
                                ? getWeaponTypeLabel(t, d.weaponType)
                                : toDisplayValue(d.weaponType)}
                            </td>
                            <td className="px-4 py-3 whitespace-nowrap text-zinc-300">
                              {d.job ? getJobLabel(t, d.job) : toDisplayValue(d.job)}
                            </td>
                          </tr>
                        );
                      })
                    : null}
                </tbody>
              </table>
            </div>
          </section>
        </div>
      </div>
    </div>
  );
}

export default DollListPage;
