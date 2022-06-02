package tech.asmussen.discord;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.object.entity.User;

import reactor.core.publisher.Mono;

public class Bot {
	
	public static void main(String[] args) {
		
		final String TOKEN = System.getenv("DISCORD_TOKEN");
		
		if (TOKEN == null)
			
			throw new RuntimeException("No token found!");
		
		DiscordClient client = DiscordClient.create(TOKEN);
		
		Mono<Void> login = client.withGateway((GatewayDiscordClient gateway) ->
				
				gateway.on(ReadyEvent.class, event ->
						
						Mono.fromRunnable(() -> {
							
							final User SELF = event.getSelf();
							
							System.out.printf("Logged in as %s#%s (%s)%n",
									SELF.getUsername(),
									SELF.getDiscriminator(),
									SELF.getId().asString()
							);
						})));
		
		login.block();
	}
}
