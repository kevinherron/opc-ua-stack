package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.types.UaDataType;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.enumerated.ComplianceLevel;

@UaDataType("SupportedProfile")
public class SupportedProfile implements UaStructure {

    public static final NodeId TypeId = Identifiers.SupportedProfile;
    public static final NodeId BinaryEncodingId = Identifiers.SupportedProfile_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.SupportedProfile_Encoding_DefaultXml;

    protected final String _organizationUri;
    protected final String _profileId;
    protected final String _complianceTool;
    protected final DateTime _complianceDate;
    protected final ComplianceLevel _complianceLevel;
    protected final String[] _unsupportedUnitIds;

    public SupportedProfile() {
        this._organizationUri = null;
        this._profileId = null;
        this._complianceTool = null;
        this._complianceDate = null;
        this._complianceLevel = null;
        this._unsupportedUnitIds = null;
    }

    public SupportedProfile(String _organizationUri, String _profileId, String _complianceTool, DateTime _complianceDate, ComplianceLevel _complianceLevel, String[] _unsupportedUnitIds) {
        this._organizationUri = _organizationUri;
        this._profileId = _profileId;
        this._complianceTool = _complianceTool;
        this._complianceDate = _complianceDate;
        this._complianceLevel = _complianceLevel;
        this._unsupportedUnitIds = _unsupportedUnitIds;
    }

    public String getOrganizationUri() {
        return _organizationUri;
    }

    public String getProfileId() {
        return _profileId;
    }

    public String getComplianceTool() {
        return _complianceTool;
    }

    public DateTime getComplianceDate() {
        return _complianceDate;
    }

    public ComplianceLevel getComplianceLevel() {
        return _complianceLevel;
    }

    public String[] getUnsupportedUnitIds() {
        return _unsupportedUnitIds;
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


    public static void encode(SupportedProfile supportedProfile, UaEncoder encoder) {
        encoder.encodeString("OrganizationUri", supportedProfile._organizationUri);
        encoder.encodeString("ProfileId", supportedProfile._profileId);
        encoder.encodeString("ComplianceTool", supportedProfile._complianceTool);
        encoder.encodeDateTime("ComplianceDate", supportedProfile._complianceDate);
        encoder.encodeEnumeration("ComplianceLevel", supportedProfile._complianceLevel);
        encoder.encodeArray("UnsupportedUnitIds", supportedProfile._unsupportedUnitIds, encoder::encodeString);
    }

    public static SupportedProfile decode(UaDecoder decoder) {
        String _organizationUri = decoder.decodeString("OrganizationUri");
        String _profileId = decoder.decodeString("ProfileId");
        String _complianceTool = decoder.decodeString("ComplianceTool");
        DateTime _complianceDate = decoder.decodeDateTime("ComplianceDate");
        ComplianceLevel _complianceLevel = decoder.decodeEnumeration("ComplianceLevel", ComplianceLevel.class);
        String[] _unsupportedUnitIds = decoder.decodeArray("UnsupportedUnitIds", decoder::decodeString, String.class);

        return new SupportedProfile(_organizationUri, _profileId, _complianceTool, _complianceDate, _complianceLevel, _unsupportedUnitIds);
    }

    static {
        DelegateRegistry.registerEncoder(SupportedProfile::encode, SupportedProfile.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(SupportedProfile::decode, SupportedProfile.class, BinaryEncodingId, XmlEncodingId);
    }

}
