document.addEventListener("DOMContentLoaded", () => {
    buscarTodasAsReservas();
});

function buscarTodasAsReservas() {
    const corpoTabela = document.getElementById("corpoTabela");
    const url = "http://localhost:8080/Reserva/getallreserva";

    fetch(url, {
        method: "GET"
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Não foi possível carregar a lista de reservas.");
        }
        return response.json();
    })
    .then(reservas => {
        // Se a lista vier vazia, avisa o usuário na tabela
        if (reservas.length === 0) {
            corpoTabela.innerHTML = '<tr><td colspan="7" class="mensagem-tabela">Nenhuma reserva encontrada no sistema.</td></tr>';
            return;
        }

        // Limpa a mensagem de "Carregando..."
        corpoTabela.innerHTML = "";

        // Percorre o array de reservas gerando as linhas dinamicamente
        reservas.forEach(reserva => {
            const linha = document.createElement("tr");

            // Extrai as informações da sala caso ela exista no objeto
            const nomeSala = reserva.sala ? reserva.sala.nome : "Não definida";
            const capacidadeSala = reserva.sala ? `${reserva.sala.capacidade} pessoas` : "-";
            const obs = reserva.observacao ? reserva.observacao : "-";

            // Monta o HTML interno da linha (as colunas do escopo)
            linha.innerHTML = `
                <td><strong>${reserva.idreserva}</strong></td>
                <td>${reserva.responsavel}</td>
                <td>${reserva.data}</td>
                <td>${reserva.inicio} às ${reserva.fim}</td>
                <td>${nomeSala}</td>
                <td>${capacidadeSala}</td>
                <td>${obs}</td>
            `;

            // Coloca a nova linha dentro do corpo da tabela
            corpoTabela.appendChild(linha);
        });
    })
    .catch(error => {
        console.error("Erro:", error);
        corpoTabela.innerHTML = `<tr><td colspan="7" class="mensagem-tabela" style="color: #dc3545;">Erro ao conectar com o servidor: ${error.message}</td></tr>`;
    });
}