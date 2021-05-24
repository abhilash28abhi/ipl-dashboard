import { Component, React } from 'react';
import { MatchDetailCard } from "../components/MatchDetailCard";
import { MatchSmallCard } from "../components/MatchSmallCard";
import { withRouter, Link } from 'react-router-dom';
import './TeamPage.css';
import {PieChart} from 'react-minimal-pie-chart'; 
import {Logo} from '../components/Logo';

class TeamPage extends Component {
    constructor(props) {
        super(props)
        this.state = { 
            team: [],
            teamName:null
        }
    }

    async componentDidMount() {
        //console.log(this.props.match.params.teamName);
        const teamName = this.props.match.params.teamName;
        const response = await fetch(`http://localhost:8080/team/${teamName}`);
        const json = await response.json();
        //console.log(json);
        this.setState({team:json, teamName: teamName});
    }

    render() {
        if (!this.state.team || !this.state.team.teamName) {
            return null;
        }
        return (
            <div>
                <Logo/>
                <div className="TeamPage">
                <div className="team-name-section"><h1 className="team-name">{this.state.team.teamName}</h1>
                </div>
                <div className="win-loss-section">
                    Wins / Losses
                    <PieChart
                        data={[
                        { title: 'Losses', value: this.state.team.totalMatches-this.state.team.totalWins, color: '#a34d5d' },
                        { title: 'Wins', value: this.state.team.totalWins, color: '#4da375' }]}
                    />
                </div>
                <div className="match-detail-section">
                    <h3>Latest Matches</h3>
                    <MatchDetailCard teamName={this.state.team.teamName} match={this.state.team.matches[0]}/>
                </div>
                {this.state.team.matches.slice(1).map(match => <MatchSmallCard key={match.id} teamName={this.state.team.teamName} match={match} />)}
                <div className="more-link">
                    <Link to={`/teams/${this.state.teamName}/matches/${process.env.REACT_APP_DATA_END_YEAR}`}>
                    More  &gt;
                    </Link>
                </div>
                </div>
            </div>
        );
    }
}

export default withRouter(TeamPage);