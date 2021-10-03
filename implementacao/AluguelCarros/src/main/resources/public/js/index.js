$(document).ready(function () {
    const id = localStorage.getItem('alugeluid')
    if(!id){
        alert('Acesso Negado!')
        window.location.href = './index.html'
    } else {
        $(".nomeusuario").text(localStorage.getItem('alugelunome'))
    }
})