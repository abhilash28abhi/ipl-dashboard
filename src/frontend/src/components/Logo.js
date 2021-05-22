import {React} from 'react';
import iplLogo from '../images/IPL-Logo.jpeg';
import './Logo.css';
import { Link } from 'react-router-dom';

export const Logo = (props) => {
    return (
        <div className="ipl-logo">
            <Link to="/"><img src={iplLogo} alt='IPL-Logo'></img></Link>
        </div>
    )
}