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
import { useTranslation } from 'react-i18next';

function DollRegisterPage() {
  const api = new DollApi();
  const { t } = useTranslation();

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
      alert(t('dollRegister.success'));
    } catch (error) {
      console.error(error);
      alert(t('dollRegister.error'));
    }
  };

  return (
    <div style={{ padding: '20px' }}>
      <h1>{t('dollRegister.title')}</h1>
      <TextField
        label={t('doll.name')}
        value={doll.name}
        onChange={(name) => setDoll({ ...doll, name })}
      />

      <SelectField
        label={t('doll.attribute')}
        value={doll.attribute}
        options={Object.values(PhaseAttribute)}
        textPrefix="enums.attributes"
        onChange={(attribute) => setDoll({ ...doll, attribute })}
      />

      <SelectField
        label={t('doll.rare')}
        value={doll.rare}
        options={Object.values(DollRare)}
        textPrefix="enums.rares"
        onChange={(rare) => setDoll({ ...doll, rare })}
      />

      <SelectField
        label={t('doll.weaponType')}
        value={doll.weaponType}
        options={Object.values(WeaponType)}
        textPrefix="enums.weaponTypes"
        onChange={(weaponType) => setDoll({ ...doll, weaponType })}
      />

      <SelectField
        label={t('doll.job')}
        value={doll.job}
        options={Object.values(Job)}
        textPrefix="enums.jobs"
        onChange={(job) => setDoll({ ...doll, job })}
      />

      <SelectField
        label={t('doll.squad')}
        value={doll.squad}
        options={Object.values(Squad)}
        textPrefix="enums.squads"
        onChange={(squad) => setDoll({ ...doll, squad })}
      />

      <h2>{t('doll.stats')}</h2>
      <NumberField
        label={t('doll.attack')}
        value={dollStat.attack}
        onChange={(attack) => setDollStat({ ...dollStat, attack })}
      />

      <NumberField
        label={t('doll.defense')}
        value={dollStat.defense}
        onChange={(defense) => setDollStat({ ...dollStat, defense })}
      />

      <NumberField
        label={t('doll.health')}
        value={dollStat.health}
        onChange={(health) => setDollStat({ ...dollStat, health })}
      />

      <NumberField
        label={t('doll.stability')}
        value={dollStat.stability}
        onChange={(stability) => setDollStat({ ...dollStat, stability })}
      />

      <NumberField
        label={t('doll.critical')}
        value={dollStat.criticalRate}
        onChange={(criticalRate) => setDollStat({ ...dollStat, criticalRate })}
      />

      <NumberField
        label={t('doll.criticalDamage')}
        value={dollStat.criticalDamage}
        onChange={(criticalDamage) => setDollStat({ ...dollStat, criticalDamage })}
      />

      <NumberField
        label={t('doll.mobility')}
        value={dollStat.mobility}
        onChange={(mobility) => setDollStat({ ...dollStat, mobility })}
      />

      <NumberField
        label={t('doll.attackBonus')}
        value={dollStat.attackBonus}
        onChange={(attackBonus) => setDollStat({ ...dollStat, attackBonus })}
      />

      <NumberField
        label={t('doll.defenseBonus')}
        value={dollStat.defenseBonus}
        onChange={(defenseBonus) => setDollStat({ ...dollStat, defenseBonus })}
      />

      <NumberField
        label={t('doll.healthBonus')}
        value={dollStat.healthBonus}
        onChange={(healthBonus) => setDollStat({ ...dollStat, healthBonus })}
      />

      <SelectField
        label={t('dollRegister.weakness1')}
        value={dollStat.weakness1}
        options={Object.values(BulletType)}
        textPrefix="enums.bulletTypes"
        onChange={(weakness1) => setDollStat({ ...dollStat, weakness1 })}
      />

      <SelectField
        label={t('dollRegister.weakness2')}
        value={dollStat.weakness2}
        options={Object.values(PhaseAttribute)}
        textPrefix="enums.attributes"
        onChange={(weakness2) => setDollStat({ ...dollStat, weakness2 })}
      />

      <button onClick={handleSubmit}>{t('dollRegister.register')}</button>
    </div>
  );
}

export default DollRegisterPage;
