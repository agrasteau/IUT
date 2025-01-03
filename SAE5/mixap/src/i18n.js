import i18n from 'i18next';
import LanguageDetector from 'i18next-browser-languagedetector';
import { initReactI18next } from 'react-i18next';
import { Language } from './enums/Language';
import translationEN from './i18n/en.json';
import translationFR from './i18n/fr.json';
import translationCH from './i18n/ch.json';
import translationDA from './i18n/da.json';
import translationEL from './i18n/el.json';
import translationAR from './i18n/ar.json';
import translationHI from './i18n/hi.json';
import translationSP from './i18n/sp.json';
import translationP from './i18n/p.json';    // Portuguese translation
import translationRU from './i18n/ru.json';  // Russian translation
import translationDE from './i18n/de.json';  // German translation
import translationKO from './i18n/ko.json';  // Korean translation
import translationIT from './i18n/it.json';  // Italian translation
import translationJAP from './i18n/jap.json'; // Japanese translation
import translationCZ from './i18n/cz.json';  // Czech Republic translation
import translationSLO from './i18n/slo.json'; // Slovakia translation
import translationH from './i18n/h.json';    // Hungarian translation
import translationRO from './i18n/ro.json';  // Romanian translation
import translationPL from './i18n/pl.json';  // Polish translation
import translationSRB from './i18n/srb.json'; // Serbian translation
import translationAL from './i18n/al.json';  // Albanian translation
import translationBG from './i18n/bg.json';  // Bulgarian translation
import translationS from './i18n/s.json';    // Swedish translation
import translationN from './i18n/n.json';    // Norwegian translation
import translationFIN from './i18n/fin.json'; // Finnish translation

let defaultLanguage = localStorage.getItem('lang') || Language.EN;

// the translations
const resources = {
  en: {
    translation: translationEN,
  },
  fr: {
    translation: translationFR,
  },
  da: {
    translation: translationDA,
  },
  hi: {
    translation: translationHI,
  },
  el: {
    translation: translationEL,
  },
  ar: {
    translation: translationAR,
  },
  sp: {
    translation: translationSP,
  },
  ch: {
    translation: translationCH,
  },
  p: {
    translation: translationP,
  },
  ru: {
    translation: translationRU,
  },
  de: {
    translation: translationDE,
  },
  ko: {
    translation: translationKO,
  },
  it: {
    translation: translationIT,
  },
  jap: {
    translation: translationJAP,
  },
  cz: {
    translation: translationCZ,
  },
  slo: {
    translation: translationSLO,
  },
  h: {
    translation: translationH,
  },
  ro: {
    translation: translationRO,
  },
  pl: {
    translation: translationPL,
  },
  srb: {
    translation: translationSRB,
  },
  al: {
    translation: translationAL,
  },
  bg: {
    translation: translationBG,
  },
  s: {
    translation: translationS,
  },
  n: {
    translation: translationN,
  },
  fin: {
    translation: translationFIN,
  },
};

i18n
  .use(LanguageDetector)
  .use(initReactI18next) // passes i18n down to react-i18next
  .init({
    resources,
    lng: defaultLanguage,

    keySeparator: '.', // to support nested translations

    interpolation: {
      escapeValue: false, // react already safes from xss
    },
  });

export default i18n;
