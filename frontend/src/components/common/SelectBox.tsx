import React, { type ChangeEvent } from 'react';

// props 타입 정의
interface CustomSelectProps {
  options: { value: string; label: string }[];
  value: string;
  onChange: (value: string) => void;
  label?: string;
}

const CustomSelect: React.FC<CustomSelectProps> = ({ options, value, onChange, label }) => {
  const handleChange = (e: ChangeEvent<HTMLSelectElement>) => {
    onChange(e.target.value);
  };

  return (
    <div>
      {label && <label>{label}</label>}
      <select
        value={value}
        onChange={handleChange}
        className="rounded border p-2" // Tailwind 예시
      >
        {options.map((option) => (
          <option key={option.value} value={option.value}>
            {option.label}
          </option>
        ))}
      </select>
    </div>
  );
};

export default CustomSelect;
