<?xml version="1.0" encoding="us-ascii"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" encoding="US-ASCII"
		doctype-public="-//W3C//DTD HTML 4.01//EN"
		doctype-system="http://www.w3.org/TR/html4/strict.dtd" indent="yes" />

	<xsl:template match="/">
		<html>
			<head>
				<link rel="stylesheet" type="text/css"
					href="{/source/request/@context-path}/orgs/{/source/org/@id}/reports/9/stream/output/" />
				<title>
					<xsl:value-of select="/source/org/@name" />
					&gt; Providers &gt;
					<xsl:value-of select="/source/provider/@name" />
				</title>
			</head>
			<body>
				<p>
					<a
						href="{/source/request/@context-path}/orgs/{/source/org/@id}/reports/0/screen/output/">
						<xsl:value-of select="/source/org/@name" />
					</a>
					&gt;
					<a
						href="{/source/request/@context-path}/orgs/{/source/org/@id}/reports/22/screen/output/">
						<xsl:value-of select="'Providers'" />
					</a>
					&gt;
					<xsl:value-of select="/source/provider/@name" />
				</p>
				<br />
				<xsl:if test="//message">
					<ul>
						<xsl:for-each select="//message">
							<li>
								<xsl:value-of select="@description" />
							</li>
						</xsl:for-each>
					</ul>
				</xsl:if>
				<table>
					<tr>
						<th>Chellow Id</th>
						<td>
							<xsl:value-of select="/source/dso/@id" />
						</td>
					</tr>
					<tr>
						<th>Name</th>
						<td>
							<xsl:value-of
								select="/source/dso/@name" />
						</td>
					</tr>

					<tr>
						<th>Participant</th>
						<td>
							<a
								href="{/source/request/@context-path}/orgs/{/source/org/@id}/reports/36/screen/output/?participant-id={/source/dso/participant/@id}">
								<xsl:value-of
									select="/source/dso/participant/@name" />
							</a>
						</td>
					</tr>
					<tr>
						<th>Role</th>
						<td>
							<a
								href="{/source/request/@context-path}/orgs/{/source/org/@id}/reports/61/screen/output/?role-id={/source/dso/market-role/@id}">
								<xsl:value-of
									select="/source/dso/market-role/@description" />
							</a>
						</td>
					</tr>
						<tr>
							<th>Code</th>
							<td>
								<xsl:value-of
									select="/source/dso/@code" />
							</td>
						</tr>
				</table>
				<br />
				<table>
					<caption>Services</caption>
					<thead>
						<tr>
							<th>Chellow Id</th>
							<th>Name</th>
							<th>Start Date</th>
							<th>Finish Date</th>
						</tr>
					</thead>
					<tbody>
						<xsl:for-each
							select="/source/dso/dso-service">
							<tr>
								<td>
									<a
										href="{/source/request/@context-path}/orgs/{/source/org/@id}/reports/57/screen/output/?contract-id={@id}">
										<xsl:value-of select="@id" />
									</a>
								</td>
								<td>
									<xsl:value-of select="@name" />
								</td>
								<td>
									<xsl:value-of
										select="concat(rate-script[@label='start']/hh-end-date[@label='start']/@year, '-', rate-script[@label='start']/hh-end-date[@label='start']/@month, '-', rate-script[@label='start']/hh-end-date[@label='start']/@day)" />
								</td>
								<td>
									<xsl:choose>
										<xsl:when
											test="rate-script[@label='finish']/hh-end-date[@label='finish']">
											<xsl:value-of
												select="concat(rate-script[@label='finish']/hh-end-date[@label='finish']/@year, '-', rate-script[@label='finish']/hh-end-date[@label='finish']/@month, '-', rate-script[@label='finish']/hh-end-date[@label='finish']/@day)" />
										</xsl:when>
										<xsl:otherwise>
											Ongoing
										</xsl:otherwise>
									</xsl:choose>
								</td>
							</tr>
						</xsl:for-each>
					</tbody>
				</table>
				<ul>
					<li>
						<a
							href="{/source/request/@context-path}/orgs/{/source/org/@id}/reports/24/screen/output/?dso-id={/source/provider/@id}">
							Line Loss Factors
						</a>
					</li>
					<li>
						<a
							href="{/source/request/@context-path}/orgs/{/source/org/@id}/reports/28/screen/output/?dso-id={/source/provider/@id}">
							MPAN top lines
						</a>
					</li>
				</ul>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>