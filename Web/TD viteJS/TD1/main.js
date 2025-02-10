import './style.css'
import javascriptLogo from './javascript.svg'
import viteLogo from '/vite.svg'
import { setupCounter } from './counter.js'
import Cookies from 'js-cookie';
import L from 'leaflet';

document.querySelector('#app').innerHTML = `

`
const couleurInput = document.getElementById("couleur");
const latitude = 48.856614;
const longitude = 2.348803;
const zoom = 13;
const map = L.map('map').setView([latitude, longitude], zoom);
L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
}).addTo(map);
L.marker([51.5, -0.09]).addTo(map)
    .bindPopup('A pretty CSS popup.<br> Easily customizable.')
    .openPopup();
//map.invalidateSize();


document.body.style.backgroundColor = Cookies.get('couleurChoisie')
couleurInput.addEventListener("change", () => {
    document.body.style.backgroundColor = couleurInput.value;
    Cookies.set('couleurChoisie', couleurInput.value);
    console.log("La nouvelle couleur est : " + couleurInput.value);
});


setupCounter(document.querySelector('#counter'))
