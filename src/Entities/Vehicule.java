package Entities;

public class Vehicule {
 private int id;
 private String imma;
 private String marque;
 private String modele;
 private String annee;
 private String categorie;
 private Integer nbLecon;
 public Vehicule(String unImma){
  imma=unImma;
 }

 public Vehicule(int id, String imma, String marque, String modele, String annee) {
  this.id = id;
  this.imma = imma;
  this.marque = marque;
  this.modele = modele;
  this.annee = annee;
  this.categorie = categorie;
 }

 public Vehicule(int id, String imma, String marque, String modele, String annee, String categorie, Integer nbLecon) {
  this.id = id;
  this.imma = imma;
  this.marque = marque;
  this.modele = modele;
  this.annee = annee;
  this.categorie = categorie;
  this.nbLecon = nbLecon;
 }

 public Vehicule(String imma, String modele) {
  this.imma = imma;
  this.modele = modele;
 }

 public Integer getNbLecon() {
  return nbLecon;
 }

 public String getMarque() {
  return marque;
 }

 public String getModele() {
  return modele;
 }

 public String getAnnee() {
  return annee;
 }

 public String getCategorie() {
  return categorie;
 }

 public int getId() {
  return id;
 }

 public String getImma() {
  return imma;
 }

}

