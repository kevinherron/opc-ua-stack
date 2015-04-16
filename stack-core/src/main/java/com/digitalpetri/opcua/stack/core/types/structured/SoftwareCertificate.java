package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.types.UaDataType;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;

@UaDataType("SoftwareCertificate")
public class SoftwareCertificate implements UaStructure {

    public static final NodeId TypeId = Identifiers.SoftwareCertificate;
    public static final NodeId BinaryEncodingId = Identifiers.SoftwareCertificate_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.SoftwareCertificate_Encoding_DefaultXml;

    protected final String _productName;
    protected final String _productUri;
    protected final String _vendorName;
    protected final ByteString _vendorProductCertificate;
    protected final String _softwareVersion;
    protected final String _buildNumber;
    protected final DateTime _buildDate;
    protected final String _issuedBy;
    protected final DateTime _issueDate;
    protected final SupportedProfile[] _supportedProfiles;

    public SoftwareCertificate() {
        this._productName = null;
        this._productUri = null;
        this._vendorName = null;
        this._vendorProductCertificate = null;
        this._softwareVersion = null;
        this._buildNumber = null;
        this._buildDate = null;
        this._issuedBy = null;
        this._issueDate = null;
        this._supportedProfiles = null;
    }

    public SoftwareCertificate(String _productName, String _productUri, String _vendorName, ByteString _vendorProductCertificate, String _softwareVersion, String _buildNumber, DateTime _buildDate, String _issuedBy, DateTime _issueDate, SupportedProfile[] _supportedProfiles) {
        this._productName = _productName;
        this._productUri = _productUri;
        this._vendorName = _vendorName;
        this._vendorProductCertificate = _vendorProductCertificate;
        this._softwareVersion = _softwareVersion;
        this._buildNumber = _buildNumber;
        this._buildDate = _buildDate;
        this._issuedBy = _issuedBy;
        this._issueDate = _issueDate;
        this._supportedProfiles = _supportedProfiles;
    }

    public String getProductName() {
        return _productName;
    }

    public String getProductUri() {
        return _productUri;
    }

    public String getVendorName() {
        return _vendorName;
    }

    public ByteString getVendorProductCertificate() {
        return _vendorProductCertificate;
    }

    public String getSoftwareVersion() {
        return _softwareVersion;
    }

    public String getBuildNumber() {
        return _buildNumber;
    }

    public DateTime getBuildDate() {
        return _buildDate;
    }

    public String getIssuedBy() {
        return _issuedBy;
    }

    public DateTime getIssueDate() {
        return _issueDate;
    }

    public SupportedProfile[] getSupportedProfiles() {
        return _supportedProfiles;
    }

    @Override
    public NodeId getTypeId() {
        return TypeId;
    }

    @Override
    public NodeId getBinaryEncodingId() {
        return BinaryEncodingId;
    }

    @Override
    public NodeId getXmlEncodingId() {
        return XmlEncodingId;
    }


    public static void encode(SoftwareCertificate softwareCertificate, UaEncoder encoder) {
        encoder.encodeString("ProductName", softwareCertificate._productName);
        encoder.encodeString("ProductUri", softwareCertificate._productUri);
        encoder.encodeString("VendorName", softwareCertificate._vendorName);
        encoder.encodeByteString("VendorProductCertificate", softwareCertificate._vendorProductCertificate);
        encoder.encodeString("SoftwareVersion", softwareCertificate._softwareVersion);
        encoder.encodeString("BuildNumber", softwareCertificate._buildNumber);
        encoder.encodeDateTime("BuildDate", softwareCertificate._buildDate);
        encoder.encodeString("IssuedBy", softwareCertificate._issuedBy);
        encoder.encodeDateTime("IssueDate", softwareCertificate._issueDate);
        encoder.encodeArray("SupportedProfiles", softwareCertificate._supportedProfiles, encoder::encodeSerializable);
    }

    public static SoftwareCertificate decode(UaDecoder decoder) {
        String _productName = decoder.decodeString("ProductName");
        String _productUri = decoder.decodeString("ProductUri");
        String _vendorName = decoder.decodeString("VendorName");
        ByteString _vendorProductCertificate = decoder.decodeByteString("VendorProductCertificate");
        String _softwareVersion = decoder.decodeString("SoftwareVersion");
        String _buildNumber = decoder.decodeString("BuildNumber");
        DateTime _buildDate = decoder.decodeDateTime("BuildDate");
        String _issuedBy = decoder.decodeString("IssuedBy");
        DateTime _issueDate = decoder.decodeDateTime("IssueDate");
        SupportedProfile[] _supportedProfiles = decoder.decodeArray("SupportedProfiles", decoder::decodeSerializable, SupportedProfile.class);

        return new SoftwareCertificate(_productName, _productUri, _vendorName, _vendorProductCertificate, _softwareVersion, _buildNumber, _buildDate, _issuedBy, _issueDate, _supportedProfiles);
    }

    static {
        DelegateRegistry.registerEncoder(SoftwareCertificate::encode, SoftwareCertificate.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(SoftwareCertificate::decode, SoftwareCertificate.class, BinaryEncodingId, XmlEncodingId);
    }

}
