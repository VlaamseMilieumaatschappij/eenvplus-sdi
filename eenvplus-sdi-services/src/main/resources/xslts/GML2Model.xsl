<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns="http://eenv.vmm.be/model"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:gco="http://www.isotc211.org/2005/gco" xmlns:gmd="http://www.isotc211.org/2005/gmd"
	xmlns:gml="http://www.opengis.net/gml/3.2" xmlns:xlink="http://www.w3.org/1999/xlink"
	xmlns:net="urn:x-inspire:specification:gmlas:Network:3.2"
	xmlns:us-net-common="http://inspire.ec.europa.eu/schemas/us-net-common/3.0"
	xmlns:us-net-sw="http://inspire.ec.europa.eu/schemas/us-net-sw/3.0"
	xmlns:eenv="http://eenv.vmm.be/">
	<xsl:variable name="us-net-common-prefix"
		select="'http://inspire.ec.europa.eu/schemas/us-net-common/3.0/'" />
	<xsl:variable name="eenv-prefix" select="'http://eenv.vmm.be/'" />

	<xsl:template match="/">
		<xsl:apply-templates select="gml:FeatureCollection" />
	</xsl:template>

	<xsl:template match="gml:FeatureCollection">
		<Riool>
			<xsl:apply-templates select="gml:featureMember/*" />
		</Riool>
	</xsl:template>

	<xsl:template match="gml:featureMember/*">
	</xsl:template>

	<!-- RioolLinks -->
	<xsl:template match="gml:featureMember/eenv:RioolLink">
		<xsl:element name="RioolLink">
			<xsl:apply-templates select="net:beginLifespanVersion"
				mode="attribute" />
			<xsl:apply-templates select="net:inspireId"
				mode="attribute" />
			<xsl:apply-templates select="eenv:rioolLinkType"
				mode="attribute" />
			<xsl:apply-templates select="us-net-sw:sewerWaterType"
				mode="attribute" />
			<xsl:apply-templates select="us-net-common:pipeDiameter"
				mode="attribute" />
			<xsl:apply-templates select="us-net-common:pressure"
				mode="attribute" />
			<xsl:apply-templates select="eenv:label" mode="attribute" />
			<xsl:apply-templates select="eenv:omschrijving"
				mode="attribute" />
			<xsl:apply-templates select="eenv:statussen"
				mode="attribute" />
			<xsl:call-template name="utilityLinkAttributes" />
		</xsl:element>
	</xsl:template>

	<xsl:template name="utilityLinkAttributes">
		<xsl:variable name="feature" select="." />
		<xsl:variable name="utilityLink"
			select="../../gml:featureMember/us-net-common:UtilityLink[$feature/net:link/@xlink:href = concat($us-net-common-prefix, 'UtilityLink/', net:inspireId/*[local-name() = 'Identifier']/*[local-name() = 'namespace'], ':' , net:inspireId/*[local-name() = 'Identifier']/*[local-name() = 'localId'])]" />
		<xsl:apply-templates select="$utilityLink/net:startNode"
			mode="attribute" />
		<xsl:apply-templates select="$utilityLink/net:endNode"
			mode="attribute" />
		<xsl:apply-templates select="$utilityLink/net:centrelineGeometry"
			mode="attribute" />
	</xsl:template>

	<!-- RioolAppurtenances -->
	<xsl:template match="gml:featureMember/eenv:RioolAppurtenance">
		<RioolAppurtenance>
			<xsl:apply-templates select="net:beginLifespanVersion"
				mode="attribute" />
			<xsl:apply-templates select="net:inspireId"
				mode="attribute" />
			<xsl:apply-templates select="us-net-common:appurtenanceType"
				mode="attribute" />
			<xsl:apply-templates select="eenv:label" mode="attribute" />
			<xsl:apply-templates select="eenv:omschrijving"
				mode="attribute" />
			<xsl:apply-templates select="eenv:statussen"
				mode="attribute" />
			<xsl:apply-templates select="eenv:koppelPunt"
				mode="attribute" />
			<xsl:apply-templates select="net:geometry" mode="attribute" />
		</RioolAppurtenance>
	</xsl:template>

	<!-- KoppelPunten -->
	<xsl:template match="gml:featureMember/eenv:KoppelPunt">
		<KoppelPunt>
			<xsl:apply-templates select="net:beginLifespanVersion"
				mode="attribute" />
			<xsl:apply-templates select="net:inspireId"
				mode="attribute" />
			<xsl:apply-templates select="net:geometry" mode="attribute" />
		</KoppelPunt>
	</xsl:template>

	<!-- Status -->
	<xsl:template match="eenv:Status">
		<Status>
			<xsl:apply-templates select="eenv:statusType" mode="attribute" />
			<xsl:apply-templates select="eenv:geldigVanaf"
				mode="attribute" />
			<xsl:apply-templates select="eenv:geldigTot"
				mode="attribute" />
		</Status>
	</xsl:template>

	<!-- Attributes -->
	<xsl:template match="*" mode="attribute">
		<xsl:element name="{local-name()}">
			<xsl:value-of select="text()" />
		</xsl:element>
	</xsl:template>

	<xsl:template match="*[@uom]" mode="attribute">
	</xsl:template>

	<xsl:template
		match="us-net-common:pipeDiameter[@uom = 'urn:ogc:def:uom:OGC::mm']"
		mode="attribute">
		<diameter>
			<xsl:value-of select="text()" />
		</diameter>
	</xsl:template>

	<xsl:template
		match="us-net-common:pipeDiameter[@uom = 'urn:ogc:def:uom:OGC::m']"
		mode="attribute">
		<diameter>
			<xsl:value-of select="text() * 1000" />
		</diameter>
	</xsl:template>

	<xsl:template
		match="us-net-common:pressure[@uom = 'urn:ogc:def:uom:OGC::bar']"
		mode="attribute">
		<pressure>
			<xsl:value-of select="text()" />
		</pressure>
	</xsl:template>

	<xsl:template match="*[@xlink:href]" mode="attribute">
		<xsl:element name="{local-name()}Id">
			<xsl:text>#</xsl:text>
			<xsl:call-template name="substring-after-slash">
				<xsl:with-param name="string"
					select="@xlink:href" />
			</xsl:call-template>
		</xsl:element>
	</xsl:template>
	
	<xsl:template match="eenv:statusType[@xlink:href]"
		mode="attribute">
		<statusId>
			<xsl:text>#</xsl:text>
			<xsl:call-template name="substring-after-slash">
				<xsl:with-param name="string"
					select="@xlink:href" />
			</xsl:call-template>
		</statusId>
	</xsl:template>

	<xsl:template match="us-net-common:appurtenanceType[@xlink:href]"
		mode="attribute">
		<rioolAppurtenanceTypeId>
			<xsl:text>#</xsl:text>
			<xsl:call-template name="substring-after-slash">
				<xsl:with-param name="string"
					select="@xlink:href" />
			</xsl:call-template>
		</rioolAppurtenanceTypeId>
	</xsl:template>

	<xsl:template match="net:startNode[@xlink:href]" mode="attribute">
		<startKoppelPuntId>
			<xsl:text>#</xsl:text>
			<xsl:call-template name="substring-after-slash">
				<xsl:with-param name="string"
					select="@xlink:href" />
			</xsl:call-template>
		</startKoppelPuntId>
	</xsl:template>

	<xsl:template match="net:endNode[@xlink:href]" mode="attribute">
		<endKoppelPuntId>
			<xsl:text>#</xsl:text>
			<xsl:call-template name="substring-after-slash">
				<xsl:with-param name="string"
					select="@xlink:href" />
			</xsl:call-template>
		</endKoppelPuntId>
	</xsl:template>

	<xsl:template match="net:inspireId" mode="attribute">
		<alternatieveId>
			<xsl:value-of
				select="*[local-name() = 'Identifier']/*[local-name() = 'localId']" />
		</alternatieveId>
		<namespaceId>
			<xsl:text>#</xsl:text>
			<xsl:value-of
				select="*[local-name() = 'Identifier']/*[local-name() = 'namespace']" />
		</namespaceId>
	</xsl:template>

	<xsl:template match="eenv:statussen" mode="attribute">
		<statussen>
			<xsl:apply-templates select="eenv:Status" />
		</statussen>
	</xsl:template>

	<xsl:template match="net:geometry | net:centrelineGeometry"
		mode="attribute">
		<geom>
			<xsl:copy-of select="*" />
		</geom>
	</xsl:template>
	
	<xsl:template name="substring-after-slash">
		<xsl:param name="string" />
		<xsl:choose>
			<xsl:when test="contains($string, '/')">
				<xsl:call-template name="substring-after-slash">
					<xsl:with-param name="string"
						select="substring-after($string, '/')" />
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$string" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>