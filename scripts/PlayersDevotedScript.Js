// JavaScript Document

function loadPlayerStats(){
$(document).ready(function(){
	$.getJSON("../assets/player_stats.json",function(data){
		var player_data = '';
		$.each(data,function(key,value){
			player_data += '<tr>';
			player_data += '<td><a href="https://www.google.com/search?q='+value.Player+'">'+ value.Player + '</a></td>';
			player_data += '<td>' + value.Assists + '</td>';
			player_data += '<td>' + value.Blocks + '</td>';
			player_data += '<td>' + value.defensiverebounds + '</td>';
			player_data += '<td>' + value.fgm + '</td>';
			player_data += '<td>' + value.fgp + '</td>';
			player_data += '<td>' + value.fta + '</td>';
			player_data += '<td>' + value.ftp + '</td>';
			player_data += '<td>' + value.gp + '</td>';
			player_data += '<td>' + value.mp + '</td>';
			player_data += '<td>' + value.or + '</td>';
			player_data += '<td>' + value.Points + '</td>';
			player_data += '<td>' + value.Steals + '</td>';
			player_data += '<td>' + value.tpm + '</td>';
			player_data += '<td>' + value.tgp + '</td>';
			player_data += '<td>' + value.Team + '</td>';
			player_data += '</tr>';
		});
		$('#players_table').append(player_data);
	});
});
}
//Pokreće se pri pozivanju javascript datoteke u head-u players.html-a
	document.onload = loadPlayerStats();

function searchByNameFunction() {
//Deklariranje varijabli za dohvaćanje
  var input, filter, table, tr, td, i, txtValue;
  input = document.getElementById("searchNameInput");
  filter = input.value.toUpperCase();
  table = document.getElementById("players_table");
  tr = table.getElementsByTagName("tr");

//Petlja kroz sve redove tablice te skrivanje onih koji ne odgovarju pretrazi
  for (i = 0; i < tr.length; i++) {
    td = tr[i].getElementsByTagName("td")[0];
    if (td) {
      txtValue = td.textContent || td.innerText;
      if (txtValue.toUpperCase().indexOf(filter) > -1) {
        tr[i].style.display = "";
      } else {
        tr[i].style.display = "none";
      }
    }
  }
}


