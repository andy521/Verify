package com.orange.verify.api;

import com.orange.verify.common.BaseEntity;
import org.hibernate.annotations.Table;

import javax.persistence.Entity;

/**
 * 软件充值卡
 */
@Entity(name = "t_card")
@Table(appliesTo = "t_card",comment = "软件充值卡")
public class Card extends BaseEntity {



}
