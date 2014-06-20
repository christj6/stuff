require 'rubygems'
require 'nokogiri' 
require 'open-uri'
require 'mechanize'
require 'fuzzystringmatch'
# require 'tk'

# automate price comparison on octopart.com

agent = Mechanize.new

#puts "Please enter a part number."
#_part = "site:www.eecontrols.com s4-405-m1" # this one returns the right url as the first one
_part = "site:www.eecontrols.com bfk-1s"

agent.get('http://www.google.com') do |page|
	search_result = page.form_with(:action => '/search') do |search|
		search.q = _part
	end.submit # end.submit doesn't work, nil:NilClass?
	
	page = Nokogiri::HTML(open(search_result.uri.to_s))
	results = page.css('div h3 a')
	# results = page.css('cite')

	puts "You searched for: "

	for i in 0..results.length-1
		link = "www.google.com" + (results[i].to_s).gsub('<a href="', "")
		link = link.split('>').first
		puts link
		#fetch = Nokogiri::HTML(open(link))
		#puts fetch.uri
		
		
		
		
		# puts (i+1).to_s + ": " + ((results[i].inner_html).gsub("<b>", "")).gsub("</b>", "")
	end
	
	# more stuff goes here
	
end

=begin
root = TkRoot.new { title "Hello, World!" }
TkLabel.new(root) do
   text 'Hello, World!'
   #pack { padx 15 ; pady 15; side 'left' }
end
Tk.mainloop
=end


