import {loginClose} from './login.js'
import {blockBackgroundHtml} from "./notice.js";

let resetPopup = document.querySelector('#popup-reset');
let closeReset = document.querySelector('#close-reset');
let openReset = document.querySelector('#reset');
let notice = document.querySelector('.notice-message');


openReset.addEventListener("click", () => {
    loginClose();
    if(!findUser()){
        notice.classList.add('show');
        blockBackgroundHtml(true)
        return;
    }
    notice.classList.remove('show');
    resetOpen();
})

closeReset.addEventListener('click', (e) => {
    resetClose(false);
})

function resetOpen() {
    resetPopup.classList.add('down', 'visible');

}

export function resetClose(flag){
    if(flag) blockResetForm();
    blockBackgroundHtml(false);
    resetPopup.classList.remove('visible', 'down');
}

function findUser(){
    return localStorage.getItem("user") != null;
}

function blockResetForm(){
    resetPopup.style.pointerEvents = "none";
}
