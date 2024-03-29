require 'rubygems'
require 'nokogiri' 
require 'open-uri'
require 'mechanize'
require 'rubyXL'

agent = Mechanize.new

workbook = RubyXL::Parser.parse("EEC PARTS.xlsx") # EEC PARTS.xlsx must be in the same directory as the ruby file
worksheet = workbook[0]

def progress(currentItem, maxItems, percentage)
	hc = (currentItem + 1).to_f/(maxItems + 1) # hc refers to "how close to done"
	if (hc - percentage).abs < 0.0001
		# it's hit
		puts (100*percentage).to_s + "% complete"
	end
end

begin
	items = 6791 # eventually will be 6791
	
	file = File.open("results.xls", "w")
	for i in 0..items-1 # excel file contains 6791 product numbers
		_part = "site:eecontrols.com filetype:pdf " + worksheet[i][0].value.to_s # doesn't get results?
		#_part = worksheet[i][0].value.to_s
		
		progress(i, items, 0.25) # displays 25% complete when 1/4 of the product numbers are processed
		progress(i, items, 0.50)
		progress(i, items, 0.75)
		progress(i, items, 0.90)

		agent.get('http://www.google.com') do |page|
			search_result = page.form_with(:action => '/search') do |search|
				search.q = _part
			end.submit
			
			#sleep (Random.rand(2.01) + 1) # (try to) prevent 503 errors
			
			page = Nokogiri::HTML(open(search_result.uri.to_s))
			results = page.css('div h3 a')
			
			#sleep (Random.rand(2.02) + 1) # prevent 503 errors

			if results.length < 1
				# can't find a pdf for the part on the official company's website
				file.write("\n")
			else
				if (results[0].to_s).split('href="').first != "<a "
					# not sure what this bug is
					# puts _part
					# puts results[0].to_s.split('href="').first
					file.write("\n")
				else
					link = "www.google.com" + (results[0].to_s).gsub('<a href="', "")
					link = link.split('">').first
					link = "http://" + link # now link looks like: http://www.google.com/url?q=http://www.eecontrols.com/documents/Page170.pdf&amp;sa=U&amp;ei=MRqoU6iCDuTK8wG4wIGACg&amp;ved=0CBQQFjAA&amp;usg=AFQjCNG6JKe0-JvfV-PK86YcF4tvhusKyQ

					fetch = Nokogiri::HTML(open(link.to_s))
					actualLink = fetch.css('div a')[0].to_s
					
					actualLink = actualLink.gsub('<a href="', "")
					actualLink = actualLink.split('">').first
					
					# awesome, now actualLink stores: http://www.eecontrols.com/documents/Page170.pdf
					
					# but first:
					# this http://www.eecontrols.com/IC-12%20Jpegs%20%20&amp;%20PDF%27s%20for%20web/p.30.pdf
					# needs to become
					# this http://www.eecontrols.com/IC-12%20Jpegs%20%20&%20PDF's%20for%20web/p.30.pdf
					# replace &amp; with &
					# replace %27 with ' (apostrophe)
					actualLink = actualLink.gsub('&amp;', '&')
					actualLink = actualLink.gsub('%27', "'")
					
					file.write(actualLink + "\n")
				end
			end
		end
		
	end 
	rescue IOError => e
		#some error occur, directory not writable etc.
	ensure
		file.close unless file == nil
end


