function loadTeams(){
	$(document).ready(function(){
	$.getJSON("../assets/teams.json",function(data){
		var team_data = '';
		$.each(data,function(key,value){
			team_data += '<tr>';
			team_data += '<td>'+ value.TeamName + '</td>'; 
			team_data += '<td>' + value.TeamAbbr + '</td>';
			team_data += '<td>' + value.Location + '</td>';
			team_data += '<td><img src="' + value.TeamLogo + '" alt="" width="100px" height="100px"></img></td>';
			team_data += '</tr>';
		});
		$('#players_table').append(team_data);
	});
});
}
document.onload = loadTeams();