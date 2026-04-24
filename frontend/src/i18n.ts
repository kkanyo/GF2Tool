import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';

// Import translation files
import en from '../public/locales/en/translation.json';
import ja from '../public/locales/ja/translation.json';
import ko from '../public/locales/ko/translation.json';

const resources = {
  ja: {
    translation: ja,
  },
  ko: {
    translation: ko,
  },
  en: {
    translation: en,
  },
};

i18n.use(initReactI18next).init({
  resources,
  lng: 'en', // default language
  fallbackLng: 'en',
  interpolation: {
    escapeValue: false, // React already escapes values
  },
  detection: {
    order: ['localStorage', 'navigator'],
    caches: ['localStorage'],
  },
});

export default i18n;
