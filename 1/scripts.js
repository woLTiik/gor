// a map of possible educations
var educationMap;

// last saved Record
var record;

/**
 * represents a record
 */
class Record {
  constructor(name, email, birthday, gender, education, hobbies) {
    this.name = name;
    this.email = email;
    this.birthday = birthday;
    this.gender = gender;
    this.education = education;
    this.hobbies = hobbies;
    record = this;
  }
  buildRow() {
    var s = "<td>";
    s+= this.name + "</td><td>";
    s+= this.email + "</td><td>";
    s+= this.birthday.getDate() + "." + (this.birthday.getMonth()+1) + "." + this.birthday.getFullYear()+ "</td><td>";
    s+= this.gender + "</td><td>";
    s+= educationMap[this.education] + "</td><td>";
    s+= this.hobbies + "</td>";
    return s;    
  }
}

/**
* Parses string from date input to Date object
*/
function parseDate(s) {
  var b = s.split(/\D/);
  return new Date(b[0], --b[1], b[2]);
}

/**
* adds a record from form to Record table
*/
function addRecord(form) {
  var name = document.forms["addDetail"]["name"].value;
  var email = document.forms["addDetail"]["email"].value;
  var date = parseDate(document.forms["addDetail"]["birthday"].value);
  var gender = document.forms["addDetail"]["gender"].value;
  var education = document.forms["addDetail"]["education"].value;
  var hobbies = "";
  if(document.forms["addDetail"]["sports"].checked){
    hobbies += document.forms["addDetail"]["sports"].value + ", ";
  }
  if(document.forms["addDetail"]["music"].checked){
    hobbies += document.forms["addDetail"]["music"].value + ", ";
  }
  if(document.forms["addDetail"]["other"].checked){
    hobbies += document.forms["addDetail"]["otherInput"].value + ", ";
  }
  var record = new Record(name, email, date, gender, education, hobbies);
  var tableRef = document.getElementById('records').getElementsByTagName('tbody')[0];
  var newRow = tableRef.insertRow(tableRef.rows.length);
  newRow.innerHTML= "<tr>" + record.buildRow() + "</tr>";
  notifyUser("Záznam pro: "+record.name + " byl vytvořen.");
}

/**
* Shows user string s given by the parameter
*/
function notifyUser(s){
  alert(s);
}


/**
* loads a map of education data from XML
*/
function parseXML() {
  let educationMap = {};
  // Create a connection to the file.
  var Connect = new XMLHttpRequest();
  // Define which file to open and
  // send the request.
  Connect.open("GET", "education.xml", false);
  Connect.setRequestHeader("Content-Type", "text/xml");
  Connect.send(null);
  // Place the response in an XML document.
  var TheDocument = Connect.responseXML;
  // Place the root node in an element.
  var Customers = TheDocument.childNodes[0];
  // Retrieve each customer in turn.
  for (var i = 2; i < Customers.children.length; i++) {
    var Customer = Customers.children[i];
    // Access each of the data values.
    var educationText = Customer.getElementsByTagName("TXT")[0].textContent.toString();
    var educationCode = Customer.getElementsByTagName("KOD")[0].textContent.toString();
    educationMap[educationCode] = educationText;
  }
  return educationMap;

}

/**
* Refreshes list of educations
*/
function refreshEducationOptions() {
  educationMap = new parseXML();
  // clear list
  var select = document.getElementById("education");
  var length = select.options.length;
  for (i = length - 1; i >= 0; i--) {
    select.options[i] = null;
  }
  //fill with xml
  for (var k in educationMap) {
    var opt = document.createElement('option');
    opt.appendChild(document.createTextNode(educationMap[k]));
    opt.value = k;
    select.appendChild(opt);
  }
}

/**
* allows/disallows text fied for other hobbies
*/
function allowOther() {
  if (document.getElementById("other").checked == true) {
    document.getElementById("otherInput").disabled = false;
  } else {
    document.getElementById("otherInput").disabled = true;
    document.getElementById("otherInput").value = "";
  }
}

/**
* Changes app color theme 
*/
function changeTheme() {
  var theme = document.getElementById("theme");
  if (theme.classList.contains('theme-dark')) {
    theme.classList.remove('theme-dark');
    theme.classList.add('theme-light');
  } else {
    theme.classList.remove('theme-light');
    theme.classList.add('theme-dark');
  }
}

