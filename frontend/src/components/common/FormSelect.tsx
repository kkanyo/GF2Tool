import { useTranslation } from 'react-i18next';

interface SelectFieldProps<T extends string> {
  label: string;
  value: T;
  options: readonly T[];
  textPrefix: string;
  onChange: (value: T) => void;
}

function SelectField<T extends string>({
  label,
  value,
  options,
  textPrefix,
  onChange,
}: SelectFieldProps<T>) {
  const { t } = useTranslation();

  return (
    <div style={{ marginBottom: '10px' }}>
      <label>{label}: </label>
      <select value={value} onChange={(e) => onChange(e.target.value as T)}>
        {options.map((option) => (
          <option key={option} value={option}>
            {t(`${textPrefix}.${option}`)}
          </option>
        ))}
      </select>
    </div>
  );
}

export { SelectField };
