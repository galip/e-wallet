package com.kn.ewallet.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@MappedSuperclass
@RequiredArgsConstructor
@Data
public class BaseEntity implements Serializable {

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATED_DATE")
    private String createdDate;

    @Column(name = "MODIFIED_BY")
    private String modifiedBy;

    @Column(name = "MODIFIED_DATE")
    private String modifiedDate;

}
