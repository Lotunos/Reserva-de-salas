// Variável global para armazenar a lista de salas que veio do servidor
let listaDeSalas = [];

// Executa assim que a página carregar
document.addEventListener("DOMContentLoaded", () => {
    carregarSalasDoBackend();
    configurarEventoSelecao();
    configurarEnvioFormulario();
});

// 1. Faz a requisição GET para listar as salas no dropdown
function carregarSalasDoBackend() {
    const selectSala = document.getElementById("idSala");

    fetch("http://localhost:8080/sala/getallsala")
        .then(response => {
            if (!response.ok) {
                throw new Error("Erro ao buscar salas do servidor.");
            }
            return response.json();
        })
        .then(salas => {
            listaDeSalas = salas; // Salva a lista na nossa variável global
            
            // Limpa a mensagem de "Carregando..."
            selectSala.innerHTML = '<option value="" disabled selected>Escolha uma sala...</option>';

            // Preenche o <select> com as opções
            salas.forEach(sala => {
                const option = document.createElement("option");
                option.value = sala.idSala; // O valor enviado no form será o ID
                option.textContent = `${sala.nome} (Capacidade: ${sala.capacidade})`;
                selectSala.appendChild(option);
            });
        })
        .catch(error => {
            console.error("Erro:", error);
            selectSala.innerHTML = '<option value="" disabled>Erro ao carregar salas</option>';
        });
}

// 2. Escuta quando o usuário muda a sala no dropdown e atualiza os campos de texto abaixo
function configurarEventoSelecao() {
    const selectSala = document.getElementById("idSala");
    const inputNome = document.getElementById("detalheNome");
    const inputCapacidade = document.getElementById("detalheCapacidade");

    selectSala.addEventListener("change", (event) => {
        const idSelecionado = parseInt(event.target.value);

        // Procura dentro da nossa lista salva a sala que tem o ID selecionado
        const salaEncontrada = listaDeSalas.find(sala => sala.idSala === idSelecionado);

        if (salaEncontrada) {
            // Preenche os inputs com as informações da sala
            inputNome.value = salaEncontrada.nome;
            inputCapacidade.value = `${salaEncontrada.capacidade} pessoas`;
        }
    });
}

// 3. Captura o envio do formulário e faz o POST transformando em JSON para o Spring
function configurarEnvioFormulario() {
    document.getElementById("formAgendamento").addEventListener("submit", function(event) {
        event.preventDefault();

        const dataInput = document.getElementById("data").value;
        
        // Converte YYYY-MM-DD para dd-MM-yyyy (esperado pelo seu @JsonFormat)
        let dataFormatada = "";
        if (dataInput) {
            const [ano, mes, dia] = dataInput.split("-");
            dataFormatada = `${dia}-${mes}-${ano}`;
        }

        // Monta o objeto idêntico aos atributos da sua classe Java
        const dadosFormulario = {
            idSala: parseInt(document.getElementById("idSala").value),
            responsavel: document.getElementById("responsavel").value,
            data: dataFormatada,
            inicio: document.getElementById("inicio").value,
            fim: document.getElementById("fim").value,
            observacao: document.getElementById("observacao").value
        };

        // Envia para a rota de criação (alimentar)
        fetch("http://localhost:8080/Reserva/criarreserva", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(dadosFormulario)
        })
        .then(response => response.text())
        .then(mensagem =>{
        alert(mensagem);
        })
    });
}