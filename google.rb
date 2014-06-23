require 'rubygems'
require 'nokogiri' 
require 'open-uri'
require 'mechanize'
require 'fuzzystringmatch'
# require 'tk'

# automate price comparison on octopart.com

agent = Mechanize.new
subAgent = Mechanize.new

#puts "Please enter a part number."
#_part = "site:www.eecontrols.com s4-405-m1" # this one returns the right url as the first one
_part = "site:www.eecontrols.com bfk-1s"

agent.get('http://www.google.com') do |page|
	search_result = page.form_with(:action => '/search') do |search|
		search.q = _part
	end.submit # end.submit doesn't work, nil:NilClass?
	
	page = Nokogiri::HTML(open(search_result.uri.to_s))
	results = page.css('div h3 a')

	puts "You searched for: "

	for i in 0..1
		link = "www.google.com" + (results[i].to_s).gsub('<a href="', "")
		link = link.split('>').first
		link = link.split('"').first # if google ever put double quotes inside their URLs, this string parsing method would cause issues
		link = "http://" + link # now link looks like: http://www.google.com/url?q=http://www.eecontrols.com/documents/Page170.pdf&amp;sa=U&amp;ei=MRqoU6iCDuTK8wG4wIGACg&amp;ved=0CBQQFjAA&amp;usg=AFQjCNG6JKe0-JvfV-PK86YcF4tvhusKyQ
		#puts link
		
		fetch = Nokogiri::HTML(open(link.to_s))
		actualLink = fetch.css('div a')[0].to_s
		
		actualLink = actualLink.gsub('<a href="', "")
		actualLink = actualLink.split('">').first
		
		puts actualLink # awesome, this returns what looks like: http://www.eecontrols.com/documents/Page170.pdf

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


