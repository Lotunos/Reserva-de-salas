document.addEventListener("DOMContentLoaded", () => {
    configurarFormularioConsulta();
});

function configurarFormularioConsulta() {
    document.getElementById("formConsulta").addEventListener("submit", function(event) {
        event.preventDefault(); // Evita o recarregamento da página

        const idReserva = document.getElementById("idReserva").value;
        const containerResultado = document.getElementById("resultadoConsulta");
        const url = `http://localhost:8080/Reserva/getreserva/${idReserva}`;

        fetch(url, {
            method: "GET" // Método HTTP padrão para consultas
        })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else if (response.status === 404) {
                throw new Error("Reserva não encontrada.");
            } else {
                throw new Error("Erro ao buscar dados no servidor.");
            }
        })
        .then(reserva => {
            // Preenche os campos de texto da tabela utilizando os ID's do HTML
            document.getElementById("res-id").textContent = reserva.idreserva;
            document.getElementById("res-responsavel").textContent = reserva.responsavel;
            document.getElementById("res-data").textContent = reserva.data;
            
            // Junta o início e o fim de forma legível (ex: 11:00 às 15:00)
            document.getElementById("res-horario").textContent = `${reserva.inicio} até ${reserva.fim}`;
            
            // Acessa o objeto interno "sala" retornado pelo seu JSON
            if (reserva.sala) {
                document.getElementById("res-sala").textContent = reserva.sala.nome;
                document.getElementById("res-capacidade").textContent = `${reserva.sala.capacidade} pessoas`;
            } else {
                document.getElementById("res-sala").textContent = "Não informada";
                document.getElementById("res-capacidade").textContent = "-";
            }
            
            // Trata o campo observação caso venha vazio ou nulo
            document.getElementById("res-observacao").textContent = reserva.observacao ? reserva.observacao : "Nenhuma";

            // Torna o container da tabela visível removendo a classe 'hidden'
            containerResultado.classList.remove("hidden");
        })
        .catch(error => {
            console.error(error);
            alert(error.message);
            // Esconde a tabela caso ocorra um erro na busca
            containerResultado.classList.add("hidden");
        });
    });
}