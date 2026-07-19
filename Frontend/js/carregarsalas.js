const botao = document.getElementById("alimentar");

botao.addEventListener("click",()=>{
    fetch("http://localhost:8080/sala/alimentar",{ 
        method: "POST",
    })   
    .then(response => response.text())
    .then(mensagem =>{
    alert(mensagem);
    })
});