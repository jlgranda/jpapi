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
package org.jpapi.model.profile;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jpapi.model.BussinesEntity;
import org.jpapi.model.Group;
import org.jpapi.util.Strings;

/**
 * Un sujeto que tiene acceso y usa el sistema. 
 * Puede ser un usuario, organización y/o sistema
 * @author jlgranda
 */

@Entity
@Table(name = "SUBJECT")
@DiscriminatorValue(value = "SBJ")
@PrimaryKeyJoinColumn(name = "id")
/*
 * Consultas nombradas para Subject
 */
@NamedQueries({
    @NamedQuery(name = "Subject.findUserByLogin", query = "select u from Subject u where u.username = :username"),
    @NamedQuery(name = "Subject.findRoleById", query = "select u.role from Subject u where u.id = :id"),
    /*@NamedQuery(name = "Subject.findGroupsByUserAndType", query = "select g FROM Subject u JOIN u.groups g WHERE u=:user and g.type=:groupType"),
     @NamedQuery(name = "Subject.findUserByGroupsAndRole", query = "select entity From Subject entity join entity.groups g where g in (:groups) and entity.role.name=:role"),*/
    @NamedQuery(name = "Subject.findUsersByNameOrUsername", query = "select u from Subject u where lower(u.username)  LIKE lower(:name) or lower(u.name) LIKE lower(:name)"),
    @NamedQuery(name = "Subject.findUserByEmail", query = "from Subject u where u.email = ?1"),
    @NamedQuery(name = "Subject.findUserByUUID", query = "from Subject u where u.uuid = ?1")})
@XmlRootElement
public class Subject extends BussinesEntity implements Serializable {

    private static final long serialVersionUID = 274770881776410973L;
    @Column(nullable = true)
    private String firstname;
    @Column(nullable = true)
    private String surname;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(length = 1024)
    @Basic(fetch = FetchType.LAZY)
    private byte[] photo;
    @Column(nullable = false, length = 128, unique = true)
    private String email;
    @ManyToOne
    private Group role;
    private String mobileNumber;
    private String workPhoneNumber;
    @Column
    private boolean confirmed;
    @Transient
    private boolean loggedIn;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_profiles_keys")
    private Set<String> identityKeys = new HashSet<String>();
    @Column
    private boolean emailSecret = true;
    @Column(length = 50)
    private String screenName;
    @Column
    private String bio;
    
    @Column(nullable = true, length = 128, unique = true)
    private String fedeEmail;
    @Column(nullable = true, length = 128)
    private String fedeEmailPassword;
    

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username != null) {
            username = username.toLowerCase();
        }
        this.username = username;
    }

    public void setPassword(String password) {
        //this.password = new BasicPasswordEncryptor().encryptPassword(password);
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean verifyPassword(String password) {
        return new BasicPasswordEncryptor().checkPassword(password,
                this.password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Group getRole() {
        return role;
    }

    public void setRole(Group role) {
        this.role = role;
    }

    public String getWorkPhoneNumber() {
        return workPhoneNumber;
    }

    public void setWorkPhoneNumber(String workPhoneNumber) {
        this.workPhoneNumber = workPhoneNumber;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(final String screenName) {
        this.screenName = screenName;
    }

    public boolean isEmailSecret() {
        return emailSecret;
    }

    public void setEmailSecret(final boolean emailSecret) {
        this.emailSecret = emailSecret;
    }

    public Set<String> getIdentityKeys() {
        return identityKeys;
    }

    public void setIdentityKeys(final Set<String> keys) {
        this.identityKeys = keys;
    }

    public boolean isUsernameConfirmed() {
        return confirmed;
    }

    public void setUsernameConfirmed(final boolean confirmed) {
        this.confirmed = confirmed;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getFedeEmail() {
        return fedeEmail;
    }

    public void setFedeEmail(String fedeEmail) {
        this.fedeEmail = fedeEmail;
    }

    public String getFedeEmailPassword() {
        return fedeEmailPassword;
    }

    public void setFedeEmailPassword(String fedeEmailPassword) {
        this.fedeEmailPassword = fedeEmailPassword;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Subject other = (Subject) obj;
        if (username == null) {
            if (other.username != null) {
                return false;
            }
        } else if (!username.equals(other.username)) {
            return false;
        }
        return true;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Transient
    public String getFullName() {
        String fullName = Strings.nullToEmpty(this.getFirstname() + " " + this.getSurname());
        boolean flag = Strings.isNullOrEmpty(this.getFirstname()) && Strings.isNullOrEmpty(this.getSurname());
        return flag ? getUsername() : fullName;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
        /*return Subject.class.getName()
                + "id=" + getId() + ","
                + "fullName=" + getFullName() + ","
                + "IdentityKeys=" + getIdentityKeys() + ","
                + " ]";*/
    }
}
