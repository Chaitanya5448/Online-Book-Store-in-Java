import {getUser} from "./navbar.js";
import {closeSuccessWindow, openSuccessWindow} from "./window.js";
import {reload} from "./main.js";



let purchase = document.querySelector('#purchase');
if(purchase != null) {
    purchase.onclick = () => {
        let checkoutDto = {
            "firstName": document.querySelector('#f-name').value,
            "lastName": document.querySelector('#l-name').value,
            "country": document.querySelector('#country').value,
            "city" : document.querySelector('#city').value,
            "street" : document.querySelector('#address').value,
            "address": addressFormatting(),
            "zip": document.querySelector('#zip').value,
            "cardNumber": document.querySelector('#card-num').value,
            "exp": document.querySelector('#exp').value,
            "ccv": document.querySelector("#ccv").value,
            "sessionid" : getUser()['sessionid']
        }
        checkoutValidation(checkoutDto);
    }
}

function addressFormatting () {
    let address = document.querySelector('#address').value;
    let country = document.querySelector('#country').value;
    let city = document.querySelector('#city').value;
    if(address === '' || country === '' || city === '') return '';

    return (address + ", " +  city + ", " + country);
}



export function executePurchase(){
    let orderDto = {
        "sessionid": getUser()['sessionid'],
    }

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/home/orders/add",
        data: JSON.stringify(orderDto),
        cache: false,
        dataType: 'json',
        responseType: 'json',
        success : () => {
            clearCart();
        }
    })
}

export function successWindowControl(){
    let success = document.querySelectorAll('#success-window')[1];
    success.style.top = "25%";
    success.style.left = "30%";
    setTimeout(openSuccessWindow, 150);
    setTimeout(closeSuccessWindow, 2100);
    setTimeout(reload, 2700);
}

export function clearCart(){
    let cartDto = {
        "sessionid" : getUser()['sessionid']
    }
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/home/cart/clear",
        data: JSON.stringify(cartDto),
        cache: false,
        dataType: 'json',
        responseType: 'json',
        success: () => {
            successWindowControl();
        }
    })
}

function checkoutValidation(checkoutDto) {
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/home/validate/checkout",
        data: JSON.stringify(checkoutDto),
        cache: false,
        dataType: 'json',
        responseType: 'json',
        success: (data) => {
            let validationErrors = JSON.parse(JSON.stringify(data));
            let errorsMap = new Map(Object.entries(validationErrors));
            if(errorsMap.size > 0) {
                highlightFields(errorsMap);
            }
            else {
                executePurchase();
            }
        }
    })
}

function highlightFields(map){
    for(let [fieldId, value] of map){
        let field = document.getElementById(fieldId);
        field.classList.add('highlight');
        controlErrorMessage(field, value);
    }
}

function controlErrorMessage(field, value){
    let saveValue = field.value;
    field.onmouseover = () => {
        if(!field.classList.contains('highlight')) return ;
        field.value = value;
        field.style.color = "red";
    }
    field.onmouseout = () => {
        if(!field.classList.contains('highlight')) return ;
        field.value = saveValue;

    }
    field.oninput = () => {
        if(!field.classList.contains('highlight')) return ;
        field.value = "";
        field.classList.remove('highlight');
        field.style.color = "black";
    }
}
