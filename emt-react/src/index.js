import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './Components/App/App';
import * as serviceWorker from './serviceWorker';
import 'bootstrap/dist/css/bootstrap.min.css';

import 'font-awesome/css/font-awesome.min.css';
import 'font-awesome/less/font-awesome.less';

import { Router } from 'react-router-dom';
import createBrowserHistory from "history/createBrowserHistory";


const history = createBrowserHistory();

ReactDOM.render(
    <Router history={history}>
        <App />
    </Router>
    , document.getElementById('root'));
