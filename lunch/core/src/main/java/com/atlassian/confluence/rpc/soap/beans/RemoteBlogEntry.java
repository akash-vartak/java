/**
 * RemoteBlogEntry.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.atlassian.confluence.rpc.soap.beans;

import javax.xml.namespace.QName;

@SuppressWarnings({"unused", "serial", "rawtypes"})
public class RemoteBlogEntry extends com.atlassian.confluence.rpc.soap.beans.RemoteBlogEntrySummary implements java.io.Serializable {

	private java.lang.String content;

	private int version;

	public RemoteBlogEntry() {
	}

	public RemoteBlogEntry(
		final long id,
		final int permissions,
		final java.lang.String space,
		final java.lang.String title,
		final java.lang.String url,
		final java.lang.String author,
		final java.util.Calendar publishDate,
		final java.lang.String content,
		final int version
	) {
		super(id, permissions, space, title, url, author, publishDate);
		this.content = content;
		this.version = version;
	}

	/**
	 * Gets the content value for this RemoteBlogEntry.
	 *
	 * @return content
	 */
	public java.lang.String getContent() {
		return content;
	}

	/**
	 * Sets the content value for this RemoteBlogEntry.
	 *
	 * @param content
	 */
	public void setContent(final java.lang.String content) {
		this.content = content;
	}

	/**
	 * Gets the version value for this RemoteBlogEntry.
	 *
	 * @return version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * Sets the version value for this RemoteBlogEntry.
	 *
	 * @param version
	 */
	public void setVersion(final int version) {
		this.version = version;
	}

	private java.lang.Object __equalsCalc = null;

	@Override
	public synchronized boolean equals(final java.lang.Object obj) {
		if (!(obj instanceof RemoteBlogEntry))
			return false;
		final RemoteBlogEntry other = (RemoteBlogEntry) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		final boolean _equals;
		_equals = super.equals(obj) && ((this.content == null && other.getContent() == null) || (this.content != null && this.content.equals(other.getContent())))
			&& this.version == other.getVersion();
		__equalsCalc = null;
		return _equals;
	}

	private boolean __hashCodeCalc = false;

	@Override
	public synchronized int hashCode() {
		if (__hashCodeCalc) {
			return 0;
		}
		__hashCodeCalc = true;
		int _hashCode = super.hashCode();
		if (getContent() != null) {
			_hashCode += getContent().hashCode();
		}
		_hashCode += getVersion();
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static final org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(RemoteBlogEntry.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("http://beans.soap.rpc.confluence.atlassian.com", "RemoteBlogEntry"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("content");
		elemField.setXmlName(new javax.xml.namespace.QName("", "content"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("version");
		elemField.setXmlName(new javax.xml.namespace.QName("", "version"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
	}

	/**
	 * Return type metadata object
	 */
	public static org.apache.axis.description.TypeDesc getTypeDesc() {
		return typeDesc;
	}

	/**
	 * Get Custom Serializer
	 */
	public static org.apache.axis.encoding.Serializer getSerializer(final Class _javaType, final QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanSerializer(_javaType, _xmlType, typeDesc);
	}

	/**
	 * Get Custom Deserializer
	 */
	public static org.apache.axis.encoding.Deserializer getDeserializer(final Class _javaType, final QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanDeserializer(_javaType, _xmlType, typeDesc);
	}

}
