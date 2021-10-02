package com.lab02.AluguelCarros.dto;

public class PedidoNovoDTO {

    private Integer clienteid;
    private Integer veiculoid;

    public PedidoNovoDTO() {
    }

    public PedidoNovoDTO(Integer clienteid, Integer veiculoid) {
        this.clienteid = clienteid;
        this.veiculoid = veiculoid;
    }

    public Integer getClienteid() {
        return this.clienteid;
    }

    public void setClienteid(Integer clienteid) {
        this.clienteid = clienteid;
    }

    public Integer getVeiculoid() {
        return this.veiculoid;
    }

    public void setVeiculoid(Integer veiculoid) {
        this.veiculoid = veiculoid;
    }

}
