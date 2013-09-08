package org.progress.crm.logic;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Apartaments")
public class Apartaments implements Serializable {

    private int id;
    private String cityName;
    private String streetName;
    private String houseNumber;
    private String buildingNumber;
    private String kladrId;
    private int typeOfSales;
    private int price;
    private int cityDistrict;
    private int floor;
    private int floors;
    private int material;
    private int sizeApartament;
    private int sizeLiving;
    private int sizeKitchen;
    private int balcony;
    private int loggia;
    private int yearOfConstruction;
    private String description;
    private boolean MethodOfPurchase_PureSale;
    private boolean MethodOfPurchase_Mortgage;
    private boolean MethodOfPurchase_Exchange;
    private boolean MethodOfPurchase_Rent;
    private boolean rePplanning;
    private String clientPhone;
    private String clientDescription;
    private Date сreationDate;
    private Date lastModify;
    private int idWorker;
    private boolean IsApproved;
    private boolean deleted;

    public Apartaments() {
    }

    public Apartaments(int typeOfSales, String cityName, String streetName, String houseNumber, String buildingNumber, String kladrId, int price, int cityDistrict, int floor, int floors, int material, int sizeApartament, int sizeLiving, int sizeKitchen, int balcony, int loggia, int yearOfConstruction, String description, boolean MethodOfPurchase_PureSale, boolean MethodOfPurchase_Mortgage, boolean MethodOfPurchase_Exchange, boolean MethodOfPurchase_Rent, boolean rePplanning, String clientPhone, String clientDescription, int idWorker, boolean IsApproved) {
        this.typeOfSales = typeOfSales;
        this.price = price;
        this.cityDistrict = cityDistrict;
        this.floor = floor;
        this.floors = floors;
        this.material = material;
        this.sizeApartament = sizeApartament;
        this.sizeLiving = sizeLiving;
        this.sizeKitchen = sizeKitchen;
        this.balcony = balcony;
        this.loggia = loggia;
        this.yearOfConstruction = yearOfConstruction;
        this.description = description;
        this.MethodOfPurchase_PureSale = MethodOfPurchase_PureSale;
        this.MethodOfPurchase_Mortgage = MethodOfPurchase_Mortgage;
        this.MethodOfPurchase_Exchange = MethodOfPurchase_Exchange;
        this.MethodOfPurchase_Rent = MethodOfPurchase_Rent;
        this.rePplanning = rePplanning;
        this.clientPhone = clientPhone;
        this.clientDescription = clientDescription;
        this.idWorker = idWorker;
        this.IsApproved = IsApproved;
        this.deleted = false;
        this.сreationDate = new Date();
        this.lastModify = new Date();
        this.cityName = cityName;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.buildingNumber = buildingNumber;
        this.kladrId = kladrId;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "CityName")
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Column(name = "StreetName")
    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    @Column(name = "HouseNumber")
    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    @Column(name = "BuildingNumber")
    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    @Column(name = "KladrId")
    public String getKladrId() {
        return kladrId;
    }

    public void setKladrId(String kladrId) {
        this.kladrId = kladrId;
    }

    @Column(name = "Deleted")
    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Column(name = "IsApproved")
    public boolean isIsApproved() {
        return IsApproved;
    }

    public void setIsApproved(boolean IsApproved) {
        this.IsApproved = IsApproved;
    }

    @Column(name = "LastModify")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLastModify() {
        return lastModify;
    }

    public void setLastModify(Date LastModify) {
        this.lastModify = LastModify;
    }

    @Column(name = "TypeOfSales")
    public int getTypeOfSales() {
        return typeOfSales;
    }

    public void setTypeOfSales(int typeOfSales) {
        this.typeOfSales = typeOfSales;
    }

    @Column(name = "Price")
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Column(name = "CityDistrict")
    public int getCityDistrict() {
        return cityDistrict;
    }

    public void setCityDistrict(int cityDistrict) {
        this.cityDistrict = cityDistrict;
    }

    @Column(name = "Floor")
    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    @Column(name = "Floors")
    public int getFloors() {
        return floors;
    }

    public void setFloors(int floors) {
        this.floors = floors;
    }

    @Column(name = "Material")
    public int getMaterial() {
        return material;
    }

    public void setMaterial(int material) {
        this.material = material;
    }

    @Column(name = "SizeApartament")
    public int getSizeApartament() {
        return sizeApartament;
    }

    public void setSizeApartament(int sizeApartament) {
        this.sizeApartament = sizeApartament;
    }

    @Column(name = "SizeLiving")
    public int getSizeLiving() {
        return sizeLiving;
    }

    public void setSizeLiving(int sizeLiving) {
        this.sizeLiving = sizeLiving;
    }

    @Column(name = "SizeKitchen")
    public int getSizeKitchen() {
        return sizeKitchen;
    }

    public void setSizeKitchen(int sizeKitchen) {
        this.sizeKitchen = sizeKitchen;
    }

    @Column(name = "Balcony")
    public int getBalcony() {
        return balcony;
    }

    public void setBalcony(int balcony) {
        this.balcony = balcony;
    }

    @Column(name = "Loggia")
    public int getLoggia() {
        return loggia;
    }

    public void setLoggia(int loggia) {
        this.loggia = loggia;
    }

    @Column(name = "YearOfConstruction")
    public int getYearOfConstruction() {
        return yearOfConstruction;
    }

    public void setYearOfConstruction(int yearOfConstruction) {
        this.yearOfConstruction = yearOfConstruction;
    }

    @Column(name = "Description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "MethodOfPurchase_PureSale")
    public boolean isMethodOfPurchase_PureSale() {
        return MethodOfPurchase_PureSale;
    }

    public void setMethodOfPurchase_PureSale(boolean MethodOfPurchase_PureSale) {
        this.MethodOfPurchase_PureSale = MethodOfPurchase_PureSale;
    }

    @Column(name = "MethodOfPurchase_Mortgage")
    public boolean isMethodOfPurchase_Mortgage() {
        return MethodOfPurchase_Mortgage;
    }

    public void setMethodOfPurchase_Mortgage(boolean MethodOfPurchase_Mortgage) {
        this.MethodOfPurchase_Mortgage = MethodOfPurchase_Mortgage;
    }

    @Column(name = "MethodOfPurchase_Exchange")
    public boolean isMethodOfPurchase_Exchange() {
        return MethodOfPurchase_Exchange;
    }

    public void setMethodOfPurchase_Exchange(boolean MethodOfPurchase_Exchange) {
        this.MethodOfPurchase_Exchange = MethodOfPurchase_Exchange;
    }

    @Column(name = "MethodOfPurchase_Rent")
    public boolean isMethodOfPurchase_Rent() {
        return MethodOfPurchase_Rent;
    }

    public void setMethodOfPurchase_Rent(boolean MethodOfPurchase_Rent) {
        this.MethodOfPurchase_Rent = MethodOfPurchase_Rent;
    }

    @Column(name = "RePlanning")
    public boolean isRePplanning() {
        return rePplanning;
    }

    public void setRePplanning(boolean rePplanning) {
        this.rePplanning = rePplanning;
    }

    @Column(name = "ClientPhone")
    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    @Column(name = "ClientDescription")
    public String getClientDescription() {
        return clientDescription;
    }

    public void setClientDescription(String clientDescription) {
        this.clientDescription = clientDescription;
    }

    @Column(name = "CreationDate")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getСreationDate() {
        return сreationDate;
    }

    public void setСreationDate(Date сreationDate) {
        this.сreationDate = сreationDate;
    }

    @Column(name = "idWorker")
    public int getIdWorker() {
        return idWorker;
    }

    public void setIdWorker(int idWorker) {
        this.idWorker = idWorker;
    }
}
