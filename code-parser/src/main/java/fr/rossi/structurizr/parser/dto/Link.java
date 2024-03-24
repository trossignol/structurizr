package fr.rossi.structurizr.parser.dto;

public record Link(String className, LinkType type, String target) {
    public enum LinkType {
        WEBSERVICE, DATABASE
    }
}
