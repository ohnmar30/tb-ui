<%
	config.require("id")
	config.require("concepts")
%>

<table  class="ke-table-vertical" id="${ config.id }" border="1">
	<thead>
		<tr>
			<th>Date </th>
			<% concepts.each { %>
			<th>${ ui.format(it) }</th>
			<% } %>
		</tr>
	</thead>
	<tbody>
			<% if (!data) { %>
			<tr>
				<td colspan="${ concepts.size() + 1 }">${ ui.message("general.none") }</td>
			</tr>
		<% } %>
		
		<% data.each { date, results -> %>
			<tr>
				<td style="vertical-align: top;"><%= kenyaui.formatDate(date) %></td>
				<% concepts.each { concept -> %>
				<td>
					<%
					def obss = results[concept]
					if (obss) {
						print obss.collect({ obs -> ui.format(obs) }).join("<br />")
					}
					%>
				</td>
				<% } %>
			</tr>
		<% } %>
	</tbody>
</table>