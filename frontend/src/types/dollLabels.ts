import type { TFunction } from 'i18next';
import type { DollRare as DollRareType, Job, PhaseAttribute, WeaponType } from '../api/generated';

export type DollLocale = 'ja' | 'ko' | 'en';

export function detectDollLocale(): DollLocale {
  // Client-side only: Vite app runs in browser for this use case.
  const language = (globalThis.navigator?.language ?? '').toLowerCase();
  if (language.startsWith('ja')) return 'ja';
  if (language.startsWith('ko')) return 'ko';
  return 'en';
}

export function getPhaseAttributeLabel(t: TFunction, value: PhaseAttribute): string {
  return t(`enums.attributes.${value}`);
}

export function getDollRareLabel(t: TFunction, value: DollRareType): string {
  return t(`enums.rares.${value}`);
}

export function getWeaponTypeLabel(t: TFunction, value: WeaponType): string {
  return t(`enums.weapons.${value}`);
}

export function getJobLabel(t: TFunction, value: Job): string {
  return t(`enums.jobs.${value}`);
}
