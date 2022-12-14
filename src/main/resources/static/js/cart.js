import {getUser} from "./navbar.js";
import {blockBackgroundHtml} from "./notice.js";
import {reload} from "./main.js";

const sumSuffix = ".00 ₽";
const shipping_cost = 170;

let homeBtn = document.getElementById('home-btn');
homeBtn.onclick = () => {
    document.location.href = "/";
}

let removeBtn = document.querySelectorAll('.remove-btn i');
removeBtn.forEach((btn) => {
    btn.onclick = () => {
    let isbnNode = btn.parentNode.parentNode.children[0].
        children[0].children[1].children[0];
    removeItem(isbnNode);
    }
})

function removeItem(isbnNode) {
    let cartDto = {
        "isbn": extractISBN(isbnNode),
        "sessionid": getUser()['sessionid'],
    }

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/home/cart/remove",
        data: JSON.stringify(cartDto),
        cache: false,
        dataType: 'json',
        responseType: 'json',
        success : () => {
           setTimeout(reload, 200);
        }
    })
}


function extractISBN(node){
    let isbn = node.innerText;
    let startIndex = 7;
    let length = (isbn.length - startIndex);
    return isbn.substr(startIndex, length);
}


let inputs = document.querySelectorAll('.quantity input');
inputs.forEach((input) => {
    input.onkeyup = () => {
        if(validInput(input)){
            let isbnNode = input.parentNode.parentNode.children[0].
                children[0].children[1].children[0]
            let cartItemDto = {
                "isbn" : extractISBN(isbnNode),
                "quantity" : input.value,
                "sessionid" : getUser()['sessionid']
            }

            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/home/cart/item/set",
                data: JSON.stringify(cartItemDto),
                cache: false,
                dataType: 'json',
                responseType: 'json',
                success: () => {
                    setItemTotal(input);
                    setCartTotal();
                }

            })
        }

    }
})


function setItemTotal(input){
    let priceField = input.parentNode.parentNode.children[2];
    let totalField = input.parentNode.parentNode.children[3];
    let price = (Number(priceField.innerText.substr(0, priceField.innerText.length - 2)));
    let inputValue = (Number(input.value))
    totalField.innerText = ((price * inputValue) + sumSuffix);
}

function setCartTotal(){
    let cartsItems = document.querySelectorAll('.cart-item');
    let itemTotal = 0;
    for(let item of cartsItems){
        let total = item.children[3].innerText;
        let substrLength = total.length - 5;
        itemTotal += (Number(total.substr(0, substrLength)));
    }
    let subtotal = document.querySelector('.subtotal .text-right div');
    subtotal.innerText = (itemTotal + sumSuffix);
    let cartTotal = document.querySelector('.total .text-right div');
    cartTotal.innerText = ((Number (itemTotal) + shipping_cost) + sumSuffix);

}

function validInput(input){
    let value = input.value;
    let stock = input.parentNode.parentNode.children[0].
        children[0].children[1].children[4];
    let stockValue = Number(stock.innerText.substr(11,  stock.innerText.length));
    let valid = (value.match(/^\d+$/) != null)
        && (((Number(value) >= 1 && Number(value) <= 9 && (Number(value)) <= stockValue)));
    if(!valid){
        input.classList.add('wrong-input');
        return false;
    }
    else {
        if (input.classList.contains('wrong-input')) {
            input.classList.remove('wrong-input');
            return true;
        }
    }
}

let checkoutBtn = document.querySelector('#checkout-btn');
checkoutBtn.onclick = () => {
    window.scrollTo({ top: 135, behavior: 'smooth' })
    if(!checkoutSaved()) {
        setTimeout(checkoutOpen, 150);
    }
    else {
        setTimeout(openCheckoutNotice, 150)
    }
}

let toCart = document.querySelectorAll('#checkout .buttons button')[0];
toCart.onclick = () => {
    setTimeout(checkoutClose, 150);
}


export function checkoutOpen(){
    let checkout = document.querySelector('#checkout');
    checkout.classList.add('active');
    blockBackgroundHtml(true);
}

function checkoutSaved(){
    let saved;
    let checkoutDto = {
        "sessionid" : getUser()['sessionid']
    }
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/home/checkout/saved",
        data: JSON.stringify(checkoutDto),
        cache: false,
        dataType: 'json',
        responseType: 'json',
        async: false,
        success: (result) => {
            saved = result;
        }
    })
    return saved;
}

function checkoutClose(){
    let checkout = document.querySelector('#checkout');
    clearCheckoutInputs();
    checkout.classList.remove('active');
    blockBackgroundHtml(false);

}

function clearCheckoutInputs(){
    let inputs = document.querySelectorAll('.checkout-container div input');
    inputs.forEach((input) => input.value = '');
}

function openCheckoutNotice() {
    let notice = document.querySelector('.checkout-notice');
    notice.classList.add("active");
}

let closeBtn = document.querySelector('.checkout-notice .notice-close-btn');
    closeBtn.onclick = () => {
        closeCheckoutNotice();
}

export function closeCheckoutNotice () {
    let notice = document.querySelector('.checkout-notice');
    notice.classList.remove("active");
}