import { Component, React } from 'react';
import { withRouter } from 'react-router-dom';
import './HomePage.css';
import { TeamTile } from '../components/TeamTile';

class HomePage extends Component {
    constructor(props) {
        super(props)
        this.state = { 
            teams: []
        }
    }

    async componentDidMount() {
        const response = await fetch(`http://localhost:8080/teams`);
        const json = await response.json();
        //console.log(json);
        this.setState({teams:json});
    }

    render() {
        return (
            <div className="HomePage">
                <div className="header-section">
                    <h1 className="application-name">IPL Dashboard</h1>
                </div>
                <div className="team-grid">
                    {this.state.teams.map(team => <TeamTile teamName={team.teamName}/>)}
                </div>
            </div>
        );
    }
}

export default withRouter(HomePage);