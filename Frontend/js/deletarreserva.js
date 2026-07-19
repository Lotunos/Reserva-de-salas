document.addEventListener("DOMContentLoaded", () => {
    configurarFormularioDelecao();
});

function configurarFormularioDelecao() {
    document.getElementById("formDelecao").addEventListener("submit", function(event) {
        event.preventDefault(); // Impede a página de recarregar

        const idReserva = document.getElementById("idReserva").value;

        // Caixa de confirmação para evitar cliques acidentais
        const confirmou = confirm(`Tem certeza que deseja deletar a reserva ID ${idReserva}?`);
        
        if (!confirmou) {
            return; // Se o usuário cancelar no pop-up, interrompe a execução
        }

        // Monta a URL concatenando o ID no final da rota (Path Variable)
        const url = `http://localhost:8080/Reserva/deletarreserva/${idReserva}`;

        fetch(url, {
            method: "DELETE" // Método HTTP correto para deleção
        })   
        .then(response => response.text())
        .then(mensagem =>{
        alert(mensagem);
        if(mensagem.includes("ok")) {
            window.location.href = "../index.html";
        }
        });
    });
}