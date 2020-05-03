package br.com.example.forum.Forum.exception;

public enum MensagensException {

    POST_NOT_FOUND("Post não encontrado"),
    USER_NOT_FOUND("Não foi informado usuário para o post"),
    SAVE_ERROR("Erro ao salvar item"),
    DELETE_ERROR("Erro ao deletar item");

    private final String text;

    private MensagensException(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
