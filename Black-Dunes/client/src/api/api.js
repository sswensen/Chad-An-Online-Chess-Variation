function get_port() {
  return (!process.env.dev) ?
    location.port :
    process.env.dev
}

export async function request(body, type, port=get_port()){
  return fetch('http://' + location.hostname + ":" + port + '/' + type, {
    method:"POST",
    body: JSON.stringify(body)
  }).then(response => {return response.json()}).catch(err => {console.error(err)});
}

export async function get_config(type, port=get_port()) {
  return fetch('http://' + location.hostname + ":" + port + '/config', {
    method:"GET"
  }).then(response => {return response.json()}).catch(err => {console.error(err)});
}