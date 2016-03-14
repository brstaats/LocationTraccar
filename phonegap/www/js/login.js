function adminLogin() {

    var pincode = document.getElementById("pincode").value;
    var auth_code = document.getElementById("auth_code").value;
    // alert(pincode + "," + auth_code);


    $.get(ip+"/AdminLogin?pincode=" + pincode + "&auth_code=" + auth_code + "", function (data) {

        data = JSON.parse(JSON.stringify(data));
        if (data.responseCode == "200") {

            createList(data, data.clientName, "notrack");

        } else {
            alert(data.responseMessage);
        }

    });
}

function userLogin() {

    var name = document.getElementById("name").value;
   
    $.get(ip+"/UserLogin?name=" + name, function (data) {

        data = JSON.parse(JSON.stringify(data));
        if (data.responseCode == "200") {

            createList(data, name, "track");

        } else {
            alert(data.responseMessage);
        }

    });
}



function createAdminFields() {

    var container = document.getElementById("fields");

    // Clear previous contents of the container
    while (container.hasChildNodes()) {
        container.removeChild(container.lastChild);

    }

    // Append a node with a random text
    container.appendChild(document.createElement("br"));
    container.appendChild(document.createTextNode("Authentication Code"));
    container.appendChild(document.createElement("br"));
    // Create an <input> element, set its type and name attributes
    var input = document.createElement("input");
    input.type = "text";
    input.setAttribute("id", "auth_code");
    container.appendChild(input);
    container.appendChild(document.createElement("br"));
    container.appendChild(document.createTextNode("Pincode"));
    container.appendChild(document.createElement("br"));

    var input = document.createElement("input");
    input.type = "text";
    input.setAttribute("id", "pincode");
    container.appendChild(input);
    container.appendChild(document.createElement("br"));
    container.appendChild(document.createElement("br"));
    var btn = document.createElement("Button");        // Create a <button> element
    var t = document.createTextNode("Login!");
    btn.onclick = adminLogin;// Create a text node
    btn.appendChild(t);                                // Append the text to <button>
    container.appendChild(btn);


}

function createUserFields() {

    var container = document.getElementById("fields");

    // Clear previous contents of the container
    while (container.hasChildNodes()) {
        container.removeChild(container.lastChild);
    }

    // Append a node with a random text
    container.appendChild(document.createElement("br"));
    container.appendChild(document.createTextNode("Name"));
    container.appendChild(document.createElement("br"));
    // Create an <input> element, set its type and name attributes
    var input = document.createElement("input");
    input.type = "text";
    input.setAttribute("id", "name");
    container.appendChild(input);
     container.appendChild(document.createElement("br"));
      container.appendChild(document.createElement("br"));
    
    var btn = document.createElement("Button");        // Create a <button> element
    var t = document.createTextNode("Login!");
    btn.onclick = userLogin;// Create a text node
    btn.appendChild(t);    
    // Append the text to <button>
    container.appendChild(btn);
    

}