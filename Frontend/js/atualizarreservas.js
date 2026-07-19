let listaDeSalas = [];

document.addEventListener("DOMContentLoaded", () => {
    carregarSalasDoBackend();
    configurarEventoSelecao();
    configurarEnvioFormulario();
});

// 1. Busca as salas para alimentar o dropdown (Igual ao fluxo de cadastro)
function carregarSalasDoBackend() {
    const selectSala = document.getElementById("idSala");

    fetch("http://localhost:8080/sala/getallsala")
        .then(response => {
            if (!response.ok) throw new Error("Erro ao buscar salas.");
            return response.json();
        })
        .then(salas => {
            listaDeSalas = salas;
            selectSala.innerHTML = '<option value="" disabled selected>Escolha uma sala...</option>';

            salas.forEach(sala => {
                const option = document.createElement("option");
                option.value = sala.idSala;
                option.textContent = `${sala.nome} (Capacidade: ${sala.capacidade})`;
                selectSala.appendChild(option);
            });
        })
        .catch(error => {
            console.error("Erro:", error);
            selectSala.innerHTML = '<option value="" disabled>Erro ao carregar salas</option>';
        });
}

// 2. Atualiza os inputs de leitura ao mudar a sala selecionada
function configurarEventoSelecao() {
    const selectSala = document.getElementById("idSala");
    const inputNome = document.getElementById("detalheNome");
    const inputCapacidade = document.getElementById("detalheCapacidade");

    selectSala.addEventListener("change", (event) => {
        const idSelecionado = parseInt(event.target.value);
        const salaEncontrada = listaDeSalas.find(sala => sala.idSala === idSelecionado);

        if (salaEncontrada) {
            inputNome.value = salaEncontrada.nome;
            inputCapacidade.value = `${salaEncontrada.capacidade} pessoas`;
        }
    });
}

// 3. Envia os dados atualizados via PUT para a API
function configurarEnvioFormulario() {
    document.getElementById("formAtualizacao").addEventListener("submit", function(event) {
        event.preventDefault();

        const dataInput = document.getElementById("data").value;
        
        // Formata a data de YYYY-MM-DD para dd-MM-yyyy conforme o Spring espera
        let dataFormatada = "";
        if (dataInput) {
            const [ano, mes, dia] = dataInput.split("-");
            dataFormatada = `${dia}-${mes}-${ano}`;
        }

        // Monta o objeto com idReserva e as outras propriedades do Java
        const dadosAtualizados = {
            idReserva: parseInt(document.getElementById("idReserva").value),
            idSala: parseInt(document.getElementById("idSala").value),
            responsavel: document.getElementById("responsavel").value,
            data: dataFormatada,
            inicio: document.getElementById("inicio").value,
            fim: document.getElementById("fim").value,
            observacao: document.getElementById("observacao").value
        };

        // Envia para a rota de atualização
        fetch("http://localhost:8080/Reserva/atualizarreserva", {
            method: "PUT", // Usando PUT que é o padrão para atualizações
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(dadosAtualizados)
        })
        .then(response => response.text())
        .then(mensagem =>{
        alert(mensagem);
        })
    });
}