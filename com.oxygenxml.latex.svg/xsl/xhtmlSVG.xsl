<?xml version="1.0" encoding="UTF-8"?>
<!--
    
Oxygen Latex to SVG sample conversion plugin
Copyright (c) 1998-2019 Syncro Soft SRL, Romania.  All rights reserved.
Licensed under the terms stated in the license file LICENSE 
available in the base directory of this plugin.

-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  version="2.0"
  xmlns:saxon="http://saxon.sf.net/"
  xmlns:converter="java:com.oxygenxml.latex.svg.LatexToSVG">
  <xsl:template match="*[contains(@class, ' topic/foreign ')][contains(@outputclass, 'embed-latex')] | *[contains(@class, ' topic/latex ')]" priority="10">
    <xsl:if test="node()">
      <span>
        <xsl:call-template name="commonattributes"/>
        <xsl:copy-of select="parse-xml(converter:convert(text()))" use-when="not(function-available('saxon:parse'))"/>
        <xsl:copy-of select="saxon:parse(converter:convert(text())" use-when="function-available('saxon:parse')"/>
      </span>
    </xsl:if>
  </xsl:template>
</xsl:stylesheet>
