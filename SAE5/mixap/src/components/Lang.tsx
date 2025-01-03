import { Select, Space } from 'antd';
import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Language } from '../enums/Language';

const Lang = () => {
  const { i18n } = useTranslation();
  const [lang, setLang] = useState<Language>(i18n.language as Language);

  const changeLanguage = (value: Language) => {
    switch (value) {
      case Language.EN:
        setLang(Language.EN);
        i18n.changeLanguage(Language.EN);
        localStorage.setItem('lang', Language.EN);
        break;
      case Language.FR:
        setLang(Language.FR);
        i18n.changeLanguage(Language.FR);
        localStorage.setItem('lang', Language.FR);
        break;
      case Language.SP:
        setLang(Language.SP);
        i18n.changeLanguage(Language.SP);
        localStorage.setItem('lang', Language.SP);
        break;
      case Language.HI:
        setLang(Language.HI);
        i18n.changeLanguage(Language.HI);
        localStorage.setItem('lang', Language.HI);
        break;
      case Language.CH:
        setLang(Language.CH);
        i18n.changeLanguage(Language.CH);
        localStorage.setItem('lang', Language.CH);
        break;
      case Language.DA:
        setLang(Language.DA);
        i18n.changeLanguage(Language.DA);
        localStorage.setItem('lang', Language.DA);
        break;
      case Language.EL:
        setLang(Language.EL);
        i18n.changeLanguage(Language.EL);
        localStorage.setItem('lang', Language.EL);
        break;
      case Language.AR:
        setLang(Language.AR);
        i18n.changeLanguage(Language.AR);
        localStorage.setItem('lang', Language.AR);
        break;
      case Language.P:
        setLang(Language.P);
        i18n.changeLanguage(Language.P);
        localStorage.setItem('lang', Language.P);
        break;
      case Language.RU:
        setLang(Language.RU);
        i18n.changeLanguage(Language.RU);
        localStorage.setItem('lang', Language.RU);
        break;
      case Language.DE:
        setLang(Language.DE);
        i18n.changeLanguage(Language.DE);
        localStorage.setItem('lang', Language.DE);
        break;
      case Language.KO:
        setLang(Language.KO);
        i18n.changeLanguage(Language.KO);
        localStorage.setItem('lang', Language.KO);
        break;
      case Language.IT:
        setLang(Language.IT);
        i18n.changeLanguage(Language.IT);
        localStorage.setItem('lang', Language.IT);
        break;
      case Language.JAP:
        setLang(Language.JAP);
        i18n.changeLanguage(Language.JAP);
        localStorage.setItem('lang', Language.JAP);
        break;
      case Language.CZ:
        setLang(Language.CZ);
        i18n.changeLanguage(Language.CZ);
        localStorage.setItem('lang', Language.CZ);
        break;
      case Language.SLO:
        setLang(Language.SLO);
        i18n.changeLanguage(Language.SLO);
        localStorage.setItem('lang', Language.SLO);
        break;
      case Language.H:
        setLang(Language.H);
        i18n.changeLanguage(Language.H);
        localStorage.setItem('lang', Language.H);
        break;
      case Language.RO:
        setLang(Language.RO);
        i18n.changeLanguage(Language.RO);
        localStorage.setItem('lang', Language.RO);
        break;
      case Language.PL:
        setLang(Language.PL);
        i18n.changeLanguage(Language.PL);
        localStorage.setItem('lang', Language.PL);
        break;
      case Language.SRB:
        setLang(Language.SRB);
        i18n.changeLanguage(Language.SRB);
        localStorage.setItem('lang', Language.SRB);
        break;
      case Language.AL:
        setLang(Language.AL);
        i18n.changeLanguage(Language.AL);
        localStorage.setItem('lang', Language.AL);
        break;
      case Language.BG:
        setLang(Language.BG);
        i18n.changeLanguage(Language.BG);
        localStorage.setItem('lang', Language.BG);
        break;
      case Language.S:
        setLang(Language.S);
        i18n.changeLanguage(Language.S);
        localStorage.setItem('lang', Language.S);
        break;
      case Language.N:
        setLang(Language.N);
        i18n.changeLanguage(Language.N);
        localStorage.setItem('lang', Language.N);
        break;
      case Language.FIN:
        setLang(Language.FIN);
        i18n.changeLanguage(Language.FIN);
        localStorage.setItem('lang', Language.FIN);
        break;
      default:
        setLang(Language.EN);
        i18n.changeLanguage(Language.EN);
        localStorage.setItem('lang', Language.EN);
        break;
    }
  }
  return (
    <Space wrap>
      <Select
        value={lang}
        onChange={changeLanguage}
        options={[
          { value: Language.AL, label: 'Albanian (Shqip)' },
          { value: Language.AR, label: 'Arabic (العربية)' },
          { value: Language.BG, label: 'Bulgarian (Български)' },
          { value: Language.CH, label: 'Chinese (中文)' },
          { value: Language.CZ, label: 'Czech (Čeština)' },
          { value: Language.DA, label: 'Danish (Dansk)' },
          { value: Language.DE, label: 'German (Deutsch)' },
          { value: Language.EL, label: 'Greek (Ελληνικά)' },
          { value: Language.EN, label: 'English' },
          { value: Language.FIN, label: 'Finnish (Suomi)' },
          { value: Language.FR, label: 'French (Français)' },
          { value: Language.H, label: 'Hungarian (Magyar)' },
          { value: Language.HI, label: 'Hindi (हिन्दी)' },
          { value: Language.IT, label: 'Italian (Italiano)' },
          { value: Language.JAP, label: 'Japanese (日本語)' },
          { value: Language.KO, label: 'Korean (한국어)' },
          { value: Language.N, label: 'Norwegian (Norsk)' },
          { value: Language.P, label: 'Portuguese (Português)' },
          { value: Language.PL, label: 'Polish (Polski)' },
          { value: Language.RO, label: 'Romanian (Română)' },
          { value: Language.RU, label: 'Russian (Русский)' },
          { value: Language.S, label: 'Swedish (Svenska)' },
          { value: Language.SLO, label: 'Slovak (Slovenčina)' },
          { value: Language.SRB, label: 'Serbian (Српски)' },
          { value: Language.SP, label: 'Spanish (Español)' },
        ]}
      />
    </Space>
  );
  
};

export default Lang;
