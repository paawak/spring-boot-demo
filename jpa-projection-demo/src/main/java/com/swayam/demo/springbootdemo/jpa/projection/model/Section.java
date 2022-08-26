package com.swayam.demo.springbootdemo.jpa.projection.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SECTION")
public class Section implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "section_text")
    private String sectionText;

    @Column(name = "style")
    private String style;

    @Column(name = "section_length")
    private int length;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getSectionText() {
	return sectionText;
    }

    public void setSectionText(String sectionText) {
	this.sectionText = sectionText;
    }

    public String getStyle() {
	return style;
    }

    public void setStyle(String style) {
	this.style = style;
    }

    public int getLength() {
	return length;
    }

    public void setLength(int length) {
	this.length = length;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + length;
	result = prime * result + ((style == null) ? 0 : style.hashCode());
	result = prime * result + ((sectionText == null) ? 0 : sectionText.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Section other = (Section) obj;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	if (length != other.length)
	    return false;
	if (style == null) {
	    if (other.style != null)
		return false;
	} else if (!style.equals(other.style))
	    return false;
	if (sectionText == null) {
	    if (other.sectionText != null)
		return false;
	} else if (!sectionText.equals(other.sectionText))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Section [id=" + id + ", text=" + sectionText + ", style=" + style + ", length=" + length + "]";
    }

}
