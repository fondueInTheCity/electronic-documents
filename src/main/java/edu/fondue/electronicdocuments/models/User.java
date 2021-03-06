package edu.fondue.electronicdocuments.models;

import edu.fondue.electronicdocuments.utils.annotations.Encrypted;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Encrypted
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Encrypted
    @Column(name = "middle_name")
    private String middleName;

    @NotBlank
    @Encrypted
    @Column(name = "last_name")
    private String lastName;

    @NotBlank
    @Column(name = "username")
    private String username;

    @NotBlank
    @Encrypted
    @Size(max = 255)
    @Email
    @Column(name = "email")
    private String email;

    @NotBlank
    @Column(name = "password", columnDefinition = "CHAR(60)")
    private String password;

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @ManyToMany
    @JoinTable(name = "users_organizations",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "organization_id"))
    private List<Organization> organizations;

    @OneToMany(mappedBy = "owner")
    private List<Organization> ownerOrganization;

    @OneToMany(mappedBy = "user")
    private List<Offer> offers;

    @ManyToMany
    @JoinTable(name = "users_organizations_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "organization_role_id"))
    private List<OrganizationRole> organizationRoles;
}
