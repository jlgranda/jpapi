/*
 * Copyright (C) 2015 jlgranda
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.jpapi.model.management;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.jpapi.model.BussinesEntity;

/**
 *
 * @author jlgranda
 */
@Entity
@Table(name = "Organization")
@DiscriminatorValue(value = "ORG")
@PrimaryKeyJoinColumn(name = "id")
public class Organization extends BussinesEntity implements Serializable {

    private static final long serialVersionUID = 3095488521256724258L;
    private String ruc;
    private String initials;
    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Owner> owners = new ArrayList<>();
//    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Theme> themes = new ArrayList<Theme>();
//    //Philosophical definition
//    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Mission> missions = new org.apache.commons.collections.list.TreeList();
//    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Vision> vissions = new org.apache.commons.collections.list.TreeList();
//    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Principle> principles = new org.apache.commons.collections.list.TreeList();

    public enum Type {
        GOVERMENT,
        PUBLIC,
        PRIVATE;
        private Type() {
        }
    }
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Organization.Type organizationType;

    public Type getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(Type organizationType) {
        this.organizationType = organizationType;
    }

    
    

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public List<Owner> getOwners() {
        return owners;
    }

    public void setOwners(List<Owner> owners) {
        this.owners = owners;
    }

    public boolean addOwner(Owner owner) {
        owner.setOrganization(this);
        return getOwners().add(owner);
    }

    public boolean removeOwner(Owner owner) {
        owner.setOrganization(null);
        return getOwners().remove(owner);
    }

//    public List<Theme> getThemes() {
//        return themes;
//    }
//
//    public void setThemes(List<Theme> themes) {
//        this.themes = themes;
//    }
//
//    public List<Mission> getMissions() {
//        return missions;
//    }
//
//    public void setMissions(List<Mission> missions) {
//        this.missions = missions;
//    }
//
//    public List<Vision> getVissions() {
//        return vissions;
//    }
//
//    public void setVissions(List<Vision> vissions) {
//        this.vissions = vissions;
//    }
//
//    public List<Principle> getPrinciples() {
//        return principles;
//    }
//
//    public void setPrinciples(List<Principle> principles) {
//        this.principles = principles;
//    }

    @Override
    public String getCanonicalPath() {
        StringBuilder path = new StringBuilder();
        path.append(getName());
        return path.toString();
    }

    @Override
    public String toString() {
        return getName();
    }

//    public boolean addMission(Mission mission) {
//        if (!this.getMissions().contains(mission)) {
//            mission.setOrganization(this);
//            return this.getMissions().add(mission);
//        }
//        return false;
//    }
//
//    public boolean addVision(Vision vision) {
//        if (!this.getVissions().contains(vision)) {
//            vision.setOrganization(this);
//            return this.getVissions().add(vision);
//        }
//        return false;
//    }
//
//    public boolean addPrinciple(Principle principle) {
//        if (!this.getPrinciples().contains(principle)) {
//            principle.setOrganization(this);
//            return this.getPrinciples().add(principle);
//        }
//        return false;
//    }
//
//    public boolean removeMission(Mission mission) {
//        mission.setOrganization(null);
//        return this.getMissions().remove(mission);
//    }
//
//    public boolean removeVision(Vision vision) {
//        vision.setOrganization(null);
//        return this.getVissions().remove(vision);
//    }
//
//    public boolean removePrinciple(Principle principle) {
//        principle.setOrganization(null);
//        return this.getPrinciples().remove(principle);
//    }
//
//    public boolean addTheme(Theme theme) {
//        if (!this.getThemes().contains(theme)) {
//            theme.setOrganization(this);
//            return this.getThemes().add(theme);
//        }
//        return false;
//    }
//    
//    public boolean removeTheme(Theme theme) {
//        theme.setOrganization(null);
//        return this.getThemes().remove(theme);
//    }
    
    public List<Organization.Type> getOrganizationTypes() {
        return Arrays.asList(Organization.Type.values());
    }
}
