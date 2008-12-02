<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" encoding="US-ASCII"
		doctype-public="-//W3C//DTD HTML 4.01//EN"
		doctype-system="http://www.w3.org/TR/html4/strict.dtd" indent="yes" />

	<xsl:template match="/">
		<html>
			<head>
				<title>
					Chellow &gt; Sites &gt;
					<xsl:value-of
						select="concat(/source/site/@code, ' ', /source/site/@name)" />
				</title>

				<link rel="stylesheet" type="text/css"
					href="{/source/request/@context-path}/style/" />
			</head>
			<body>
				<p>
					<a href="{/source/request/@context-path}/">
						<img
							src="{/source/request/@context-path}/logo/" />
						<span class="logo">Chellow</span>
					</a>
					&gt;
					<a href="{/source/request/@context-path}/sites/">
						<xsl:value-of select="'Sites'" />
					</a>
					&gt;
					<xsl:value-of
						select="concat(/source/site/@code, ' ', /source/site/@name, ' [')" />
					<a
						href="{/source/request/@context-path}/reports/3/output/?site-id={/source/site/@id}">
						<xsl:value-of select="'view'" />
					</a>
					<xsl:value-of select="']'" />
				</p>
				<xsl:if test="//message">
					<ul>
						<xsl:for-each select="//message">
							<li>
								<xsl:value-of select="@description" />
							</li>
						</xsl:for-each>
					</ul>
				</xsl:if>
				<xsl:choose>
					<xsl:when
						test="/source/request/@method='get' and /source/request/parameter[@name='view']/value='confirm-delete'">
						<form method="post" action=".">
							<fieldset>
								<legend>
									Are you sure you want to delete this
									site and any associated snags?
								</legend>
								<input type="submit" name="delete"
									value="Delete" />
							</fieldset>
						</form>
						<p>
							<a href=".">Cancel</a>
						</p>
					</xsl:when>
					<xsl:when
						test="/source/response/@status-code = '201'">
						<p>
							The
							<a
								href="{/source/response/header[@name = 'Location']/@value}">
								<xsl:value-of select="'new supply'" />
							</a>
							has been successfully created.
						</p>
					</xsl:when>
					<xsl:otherwise>
						<br />
						<form action="." method="post">
							<fieldset>
								<legend>Update this site</legend>
								<label>
									Name
									<xsl:value-of select="' '" />
									<input name="name"
										value="{/source/site/@name}" />
								</label>
								<br />
								<label>
									Code
									<xsl:value-of select="' '" />
									<input name="code"
										value="{/source/site/@code}" />
								</label>
								<br />
								<br />
								<input name="update" type="submit"
									value="Update" />
								<input type="reset" value="Reset" />
							</fieldset>
						</form>

						<br />
						<form action=".">
							<fieldset>
								<input type="hidden" name="view"
									value="confirm-delete" />
								<legend>Delete this site</legend>
								<input type="submit" value="Delete" />
							</fieldset>
						</form>
						<br />
						<table>
							<caption>Supply generations</caption>
							<tr>
								<th>Chellow Id</th>
								<th>Supply</th>
								<th>Import MPAN core</th>
								<th>Export MPAN core</th>
								<th>From</th>
								<th>To</th>
							</tr>
							<xsl:for-each
								select="/source/site/supply-generation">
								<tr>
									<td>
										<a
											href="{/source/request/@context-path}/supplies/{supply/@id}/generations/{@id}/">
											<xsl:value-of select="@id" />
										</a>
									</td>
									<td>
										<a
											href="{/source/request/@context-path}/supplies/{supply/@id}/">
											<xsl:value-of
												select="supply/@id" />
										</a>
									</td>
									<td>
										<xsl:if
											test="mpan[llfc/@is-import='true']">
											<xsl:value-of
												select="mpan[llfc/@is-import='true']/mpan-core/@core" />
										</xsl:if>
									</td>
									<td>
										<xsl:if
											test="mpan[llfc/@is-import='false']">
											<xsl:value-of
												select="mpan[llfc/@is-import='false']/mpan-core/@core" />
										</xsl:if>
									</td>
									<td>
										<xsl:value-of
											select="concat(hh-end-date[@label='start']/@year, '-', hh-end-date[@label='start']/@month, '-', hh-end-date[@label='start']/@day, ' ', hh-end-date[@label='start']/@hour, ':', hh-end-date[@label='start']/@minute, 'Z')" />
									</td>
									<td>
										<xsl:choose>
											<xsl:when
												test="hh-end-date[@label='finish']">
												<xsl:value-of
													select="concat(hh-end-date[@label='finish']/@year, '-', hh-end-date[@label='finish']/@month, '-', hh-end-date[@label='finish']/@day, ' ', hh-end-date[@label='finish']/@hour, ':', hh-end-date[@label='finish']/@minute, 'Z')" />
											</xsl:when>
											<xsl:otherwise>
												Ongoing
											</xsl:otherwise>
										</xsl:choose>
									</td>
								</tr>
							</xsl:for-each>
						</table>
						<!-- 
							<br />
							<form method="post"
							action="{/source/Request/@contextPath}/editor/insertSupplySite">
							<fieldset>
							<legend>
							Attach an existing supply to this site
							</legend>
							<label>
							<xsl:value-of select="'MPAN core '" />
							<input name="mpanCore"
							value="{/source/request/parameter[@name='mpanCore']}" />
							</label>
							<input type="submit" value="Attach" />
							</fieldset>
							</form>
						-->
						<br />
						<form action="." method="post">
							<fieldset>
								<legend>Insert a supply</legend>
								<br />
								<label>
									Source
									<select name="source-id">
										<xsl:for-each
											select="/source/source">
											<option value="{@id}">
												<xsl:if
													test="/source/request/parameter[@name='source-id']/value = @id">
													<xsl:attribute
														name="selected">
																<xsl:value-of
															select="'selected'" />
															</xsl:attribute>
												</xsl:if>
												<xsl:value-of
													select="concat(@code, ' : ', @name)" />
											</option>
										</xsl:for-each>
									</select>
								</label>
								<br />
								<label>
									<xsl:value-of select="'Name '" />
									<input name="name"
										value="{/source/request/parameter[@name = 'name']/value}" />
								</label>
								<br />
								<br />
								<fieldset>
									<legend>Start Date</legend>
									<input name="start-date-year"
										maxlength="4" size="4">
										<xsl:attribute name="value">
											<xsl:choose>
												<xsl:when
													test="/source/request/parameter[@name='start-date-year']">
													<xsl:value-of
														select="/source/request/parameter[@name='start-date-year']/value" />
												</xsl:when>
												<xsl:otherwise>
													<xsl:value-of
														select="/source/date/@year" />
												</xsl:otherwise>
											</xsl:choose>
										</xsl:attribute>
									</input>
									<xsl:value-of select="' - '" />
									<select name="start-date-month">
										<xsl:for-each
											select="/source/months/month">
											<option value="{@number}">
												<xsl:choose>
													<xsl:when
														test="/source/request/parameter[@name='start-date-month']">
														<xsl:if
															test="/source/request/parameter[@name='start-date-month']/value = @number">
															<xsl:attribute
																name="selected">
																<xsl:value-of
																	select="'selected'" />
															</xsl:attribute>
														</xsl:if>
													</xsl:when>
													<xsl:otherwise>
														<xsl:if
															test="/source/date/@month = @number">
															<xsl:attribute
																name="selected">
																<xsl:value-of
																	select="'selected'" />
															</xsl:attribute>
														</xsl:if>
													</xsl:otherwise>
												</xsl:choose>
												<xsl:value-of
													select="@number" />
											</option>
										</xsl:for-each>
									</select>
									<xsl:value-of select="' - '" />
									<select name="start-date-day">
										<xsl:for-each
											select="/source/days/day">
											<option value="{@number}">
												<xsl:choose>
													<xsl:when
														test="/source/request/parameter[@name='start-date-day']">
														<xsl:if
															test="/source/request/parameter[@name='start-date-day']/value = @number">
															<xsl:attribute
																name="selected">
																<xsl:value-of
																	select="'selected'" />
															</xsl:attribute>
														</xsl:if>
													</xsl:when>
													<xsl:otherwise>
														<xsl:if
															test="/source/date/@day = @number">
															<xsl:attribute
																name="selected">
																<xsl:value-of
																	select="'selected'" />
															</xsl:attribute>
														</xsl:if>
													</xsl:otherwise>
												</xsl:choose>
												<xsl:value-of
													select="@number" />
											</option>
										</xsl:for-each>
									</select>
								</fieldset>
								<br />
								<label>
									<xsl:value-of
										select="'Meter Serial Number '" />
									<input name="meter-serial-number"
										value="{/source/request/parameter[@name = 'meter-serial-number']/value}" />
								</label>
								<br />
								<br />
								<fieldset>
									<legend>Import</legend>
									<label>
										<xsl:value-of select="'Mpan '" />
										<input name="import-mpan"
											size="35"
											value="{/source/request/parameter[@name = 'import-mpan']/value}" />
									</label>
									<br />
									<label>
										<xsl:value-of select="'SSC '" />
										<input name="import-ssc-code"
											value="{/source/request/parameter[@name = 'import-ssc-code']/value}" />
									</label>
									<br />
									<label>
										<xsl:value-of
											select="'GSP Group '" />
										<select
											name="import-gsp-group-id">
											<xsl:for-each
												select="/source/gsp-group">
												<option value="{@id}">
													<xsl:if
														test="@id = /source/request/parameter[@name='import-gsp-group-id']/value">
														<xsl:attribute
															name="selected" />
													</xsl:if>
													<xsl:value-of
														select="concat(@code, ' ', @description)" />
												</option>
											</xsl:for-each>
										</select>
									</label>
									<br />
									<label>
										<xsl:value-of
											select="'Agreed Supply Capacity '" />
										<input
											name="import-agreed-supply-capacity"
											value="{/source/request/parameter[@name = 'import-agreed-supply-capacity']/value}" />
									</label>
									<br />
									<label>
										<xsl:value-of
											select="'HHDC Contract Name '" />
										<input
											name="import-hhdc-contract-name"
											value="{/source/request/parameter[@name = 'import-hhdc-contract-name']/value}" />
									</label>
									<br />
									<label>
										<xsl:value-of
											select="'HHDC Account Reference '" />
										<input
											name="import-hhdc-account-reference"
											value="{/source/request/parameter[@name = 'import-hhdc-account-reference']/value}" />
									</label>
									<br />
									<label>
										<xsl:value-of
											select="'Supplier Contract Name '" />
										<input
											name="import-supplier-contract-name"
											value="{/source/request/parameter[@name = 'import-supplier-contract-name']/value}" />
									</label>
									<br />
									<label>
										<xsl:value-of
											select="'Supplier Account Reference '" />
										<input
											name="import-supplier-account-reference"
											value="{/source/request/parameter[@name = 'import-hhdc-account-reference']/value}" />
									</label>
								</fieldset>
								<br />
								<fieldset>
									<legend>Export</legend>
									<label>
										<xsl:value-of select="'Mpan '" />
										<input name="export-mpan"
											size="35"
											value="{/source/request/parameter[@name = 'export-mpan']/value}" />
									</label>
									<br />
									<label>
										<xsl:value-of select="'SSC '" />
										<input name="export-ssc-code"
											value="{/source/request/parameter[@name = 'export-ssc-code']/value}" />
									</label>
									<br />
									<label>
										<xsl:value-of
											select="'GSP Group '" />
										<select
											name="export-gsp-group-id">
											<xsl:for-each
												select="/source/gsp-group">
												<option value="{@id}">
													<xsl:if
														test="@id = /source/request/parameter[@name='export-gsp-group-id']/value">
														<xsl:attribute
															name="selected" />
													</xsl:if>
													<xsl:value-of
														select="concat(@code, ' ', @description)" />
												</option>
											</xsl:for-each>
										</select>
									</label>
									<br />
									<label>
										<xsl:value-of
											select="'Agreed Supply Capacity '" />
										<input
											name="export-agreed-supply-capacity"
											value="{/source/request/parameter[@name = 'export-agreed-supply-capacity']/value}" />
									</label>
									<br />
									<label>
										<xsl:value-of
											select="'HHDC Contract Name'" />
										<input
											name="export-hhdc-contract-name"
											value="{/source/request/parameter[@name = 'export-hhdc-contract-name']/value}" />
									</label>
									<br />
									<label>
										<xsl:value-of
											select="'HHDC Account Reference'" />
										<input
											name="export-hhdc-account-reference"
											value="{/source/request/parameter[@name = 'export-hhdc-account-reference']/value}" />
									</label>
									<br />
									<label>
										<xsl:value-of
											select="'Supplier Contract Name '" />
										<input
											name="export-supplier-contract-name"
											value="{/source/request/parameter[@name = 'export-supplier-contract-name']/value}" />
									</label>
									<br />
									<label>
										<xsl:value-of
											select="'Supplier Account Reference '" />
										<input
											name="export-supplier-account-reference"
											value="{/source/request/parameter[@name = 'export-supplier-account-reference']/value}" />
									</label>
								</fieldset>
								<br />
								<input name="insert" type="submit"
									value="Insert" />
								<input type="reset" value="Reset" />
							</fieldset>
						</form>
					</xsl:otherwise>
				</xsl:choose>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>