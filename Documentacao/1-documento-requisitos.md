# Documento de Requisitos: To-Do List Simples

## 1. Introdução
Este documento especifica os Requisitos Funcionais e Não Funcionais do aplicativo **To-Do List Simples**, um aplicativo Android desenvolvido para gerenciar tarefas diárias de forma rápida, intuitiva e descomplicada.

---

## 2. Requisitos Funcionais (RF)
Os requisitos funcionais descrevem as funcionalidades que o sistema deve executar para atender às necessidades do usuário.

| Código | Descrição | Prioridade |
| :--- | :--- | :--- |
| **RF01** | O usuário deve poder **cadastrar** uma nova tarefa informando um título (e opcionalmente uma descrição/detalhes). | Alta |
| **RF02** | O sistema deve **listar** todas as tarefas cadastradas em ordem de criação ou exibidas em uma interface de lista clara e rolagem eficiente (`RecyclerView` / Adapters). | Alta |
| **RF03** | O usuário deve poder **editar** as informações de uma tarefa existente (título ou descrição). | Alta |
| **RF04** | O usuário deve poder **excluir** uma tarefa da lista quando ela não for mais necessária ou tiver sido concluída. | Alta |
| **RF05** | O sistema deve validar campos obrigatórios (ex: impedir o cadastro de tarefas com título vazio). | Média |

---

## 3. Requisitos Não Funcionais (RNF)
Os requisitos não funcionais definem os critérios de qualidade, desempenho, usabilidade e restrições do sistema.

| Código | Descrição | Categoria |
| :--- | :--- | :--- |
| **RNF01** | **Usabilidade**: A interface do usuário deve ser limpa, intuitiva e seguir diretrizes modernas de design (Material Design), permitindo que qualquer usuário realize operações básicas sem treinamento prévio. | Usabilidade |
| **RNF02** | **Desempenho**: As operações de cadastro, edição, exclusão e listagem devem ocorrer instantaneamente, sem atrasos perceptíveis para o usuário. | Desempenho |
| **RNF03** | **Compatibilidade**: O aplicativo deve ser compatível com as versões modernas do sistema operacional Android (API 24 ou superior recomendada). | Compatibilidade |
| **RNF04** | **Persistência de Dados**: As tarefas cadastradas devem ser mantidas localmente no dispositivo para que não sejam perdidas ao fechar o aplicativo. | Armazenamento |
| **RNF05** | **Manutenibilidade**: O código-fonte deve ser estruturado de forma modular (separação entre adaptadores, telas/atividades e modelos de dados), facilitando futuras manutenções e evoluções. | Qualidade de Software |
