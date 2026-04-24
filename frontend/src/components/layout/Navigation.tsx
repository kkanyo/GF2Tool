import { Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import SelectBox from '../common/SelectBox';

function Navigation() {
  const { t, i18n } = useTranslation();

  const localeOptions = [
    { value: 'ko', label: '🇰🇷 한국어' },
    { value: 'en', label: '🇺🇸 English' },
    { value: 'ja', label: '🇯🇵 日本語' },
  ];

  return (
    <nav className="border-b border-zinc-800 bg-zinc-950/80 backdrop-blur">
      <div className="mx-auto flex w-full max-w-6xl items-center justify-between px-4 py-3">
        <div className="text-sm font-semibold tracking-tight text-zinc-100">{t('app.title')}</div>
        <div className="flex items-center gap-3 text-sm">
          <Link
            className="rounded-md px-2 py-1 text-zinc-300 hover:bg-zinc-900 hover:text-zinc-100"
            to="/dolls/list"
          >
            {t('navigation.dollList')}
          </Link>
          <Link
            className="rounded-md px-2 py-1 text-zinc-300 hover:bg-zinc-900 hover:text-zinc-100"
            to="/dolls/register"
          >
            {t('navigation.dollRegister')}
          </Link>

          <SelectBox
            options={localeOptions}
            value={i18n.language}
            onChange={(value) => i18n.changeLanguage(value)}
          />
        </div>
      </div>
    </nav>
  );
}

export { Navigation };
