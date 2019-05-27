
export const ACCESS_TOKEN = 'accessToken';

const request = (options) => {
    const headers = new Headers({
        'Content-Type': 'application/json',
    })

    if(localStorage.getItem(ACCESS_TOKEN)) {
        headers.append('Authorization', 'Bearer ' + localStorage.getItem(ACCESS_TOKEN))
    }

    const defaults = {headers: headers};
    options = Object.assign({}, defaults, options);

    return fetch(options.url, options)
        .then(response =>
            response.json().then(json => {
                if(!response.ok) {
                    return Promise.reject(json);
                }
                return json;
            })
        );
};


export function login(loginRequest) {
    return request({
        url: "http://localhost:8080/login",
        method: 'POST',
        body: JSON.stringify({
            email: loginRequest.email,
            password: loginRequest.password

        })
    });
}

export const createUser = (user) => {
    return fetch('http://localhost:8080/sign_up', {

        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            email: user.email,
            password: user.pwd,
            level: user.level,
            full_name: user.fullname
        })

    });
};

export const doesUserExist = (user) => {
    return fetch('http://localhost:8080/user/exists/'+user.email,  {
        method: 'GET',
        headers: {
            'Content-Type': 'text/plain'
        }
    });
};


export const getCurrentUser = () => {
    return request({
        url: "http://localhost:8080/current_user",
        method: 'GET'
    });
};

export const updateUserInfo = (user) => {

    const headers = new Headers({
        'Content-Type': 'application/json',
    })

    if(localStorage.getItem(ACCESS_TOKEN)) {
        headers.append('Authorization', 'Bearer ' + localStorage.getItem(ACCESS_TOKEN))
    }

    return fetch("http://localhost:8080/user", {
        method: 'PATCH',
        headers: headers,
        body: JSON.stringify({
            id: user.id,
            email: user.email,
            password: user.pwd,
            userName: user.username,
            fullName: user.fullname
        })
    });
};