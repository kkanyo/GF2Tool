import { useState } from 'react';
import {
  BulletType,
  DollApi,
  DollRare,
  Job,
  PhaseAttribute,
  Squad,
  WeaponType,
  type DollSaveRequestDto,
  type DollStatSaveRequestDto,
  type DollWithStatSaveRequestDto,
} from '../../api/generated';
import { NumberField, SelectField, TextField } from '../../components/common/Index';

function DollRegisterPage() {
  const api = new DollApi();

  // 초기값을 명시적으로 설정하면 `undefined` 접근 에러를 방지
  const [doll, setDoll] = useState<DollSaveRequestDto>({
    name: '',
    attribute: PhaseAttribute.None,
    rare: DollRare.Standard,
    weaponType: WeaponType.Hg,
    job: Job.Sentinel,
    squad: Squad.None,
  });

  const [dollStat, setDollStat] = useState<DollStatSaveRequestDto>({
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
  });

  const handleSubmit = async () => {
    try {
      const requestDto: DollWithStatSaveRequestDto = { doll, dollStat };
      await api.createDoll(requestDto);
      alert('Doll data submitted successfully!');
    } catch (error) {
      console.error(error);
      alert('Failed to submit doll data.');
    }
  };

  return (
    <div style={{ padding: '20px' }}>
      <h1>인형 정보 등록</h1>
      <TextField label="이름" value={doll.name} onChange={(name) => setDoll({ ...doll, name })} />

      <SelectField
        label="속성"
        value={doll.attribute}
        options={Object.values(PhaseAttribute)}
        onChange={(attribute) => setDoll({ ...doll, attribute })}
      />

      <SelectField
        label="희귀도"
        value={doll.rare}
        options={Object.values(DollRare)}
        onChange={(rare) => setDoll({ ...doll, rare })}
      />

      <SelectField
        label="무기 타입"
        value={doll.weaponType}
        options={Object.values(WeaponType)}
        onChange={(weaponType) => setDoll({ ...doll, weaponType })}
      />

      <SelectField
        label="직업"
        value={doll.job}
        options={Object.values(Job)}
        onChange={(job) => setDoll({ ...doll, job })}
      />

      <SelectField
        label="소대"
        value={doll.squad}
        options={Object.values(Squad)}
        onChange={(squad) => setDoll({ ...doll, squad })}
      />

      <h2>스탯 정보</h2>
      <NumberField
        label="공격"
        value={dollStat.attack}
        onChange={(attack) => setDollStat({ ...dollStat, attack })}
      />

      <NumberField
        label="방어"
        value={dollStat.defense}
        onChange={(defense) => setDollStat({ ...dollStat, defense })}
      />

      <NumberField
        label="체력"
        value={dollStat.health}
        onChange={(health) => setDollStat({ ...dollStat, health })}
      />

      <NumberField
        label="안정 지수"
        value={dollStat.stability}
        onChange={(stability) => setDollStat({ ...dollStat, stability })}
      />

      <NumberField
        label="치명타"
        value={dollStat.criticalRate}
        onChange={(criticalRate) => setDollStat({ ...dollStat, criticalRate })}
      />

      <NumberField
        label="치명타 피해"
        value={dollStat.criticalDamage}
        onChange={(criticalDamage) => setDollStat({ ...dollStat, criticalDamage })}
      />

      <NumberField
        label="이동력"
        value={dollStat.mobility}
        onChange={(mobility) => setDollStat({ ...dollStat, mobility })}
      />

      <NumberField
        label="공격 보너스"
        value={dollStat.attackBonus}
        onChange={(attackBonus) => setDollStat({ ...dollStat, attackBonus })}
      />

      <NumberField
        label="방어 보너스"
        value={dollStat.defenseBonus}
        onChange={(defenseBonus) => setDollStat({ ...dollStat, defenseBonus })}
      />

      <NumberField
        label="체력 보너스"
        value={dollStat.healthBonus}
        onChange={(healthBonus) => setDollStat({ ...dollStat, healthBonus })}
      />

      <SelectField
        label="탄종 약점"
        value={dollStat.weakness1}
        options={Object.values(BulletType)}
        onChange={(weakness1) => setDollStat({ ...dollStat, weakness1 })}
      />

      <SelectField
        label="위상 속성 약점"
        value={dollStat.weakness2}
        options={Object.values(PhaseAttribute)}
        onChange={(weakness2) => setDollStat({ ...dollStat, weakness2 })}
      />

      <button onClick={handleSubmit}>등록하기</button>

      {/* 간단한 상태 확인용 JSON 디버그 */}
      <h2>디버그</h2>
      <pre>{JSON.stringify({ doll, dollStat }, null, 2)}</pre>
    </div>
  );
}

export default DollRegisterPage;
